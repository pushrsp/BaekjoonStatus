package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.api.dto.AuthDto;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemWriteService;
import project.BaekjoonStatus.shared.domain.tag.service.TagWriteService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserReadService;
import project.BaekjoonStatus.shared.domain.user.service.UserWriteService;
import project.BaekjoonStatus.shared.dto.command.ProblemCommand;
import project.BaekjoonStatus.shared.dto.command.TagCommand;
import project.BaekjoonStatus.shared.dto.command.UserCommand;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.dto.response.SolvedAcUserResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserReadService userReadService;
    private final UserWriteService userWriteService;
    private final TagWriteService tagWriteService;
    private final ProblemWriteService problemWriteService;

    public void duplicateUsername(String username) {
        boolean isPresent = userReadService.existByUsername(username);
        if(isPresent)
            throw new MyException(CodeEnum.USER_DUPLICATE);
    }

    public AuthDto.SolvedCountResp validBaekjoonUsername(String username) {
        SolvedAcUserResp info = new SolvedAcHttp().getBaekjoonUser(username);
        if(info == null)
            throw new MyException(CodeEnum.SOLVED_AC_USER_NOT_FOUND);

        return new AuthDto.SolvedCountResp(info.getSolvedCount());
    }

    @Transactional
    public void create(AuthDto.SignupReq signupReq) {
        BaekjoonCrawling crawling = new BaekjoonCrawling(signupReq.getBaekjoonUsername());
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();

        List<Long> solvedHistories = crawling.getMySolvedHistories();
        List<SolvedAcProblemResp> problemInfos = solvedAcHttp.getProblemsByProblemIds(solvedHistories);

        List<TagCommand> tagCommands = TagCommand.toCommandFromSolvedProblems(problemInfos);
        Map<String, UUID> tags = tagWriteService.bulkInsertByTagCommands(tagCommands);

        List<ProblemCommand> problemCommands = ProblemCommand.toCommandFromSolvedProblemsAndTags(problemInfos, tags);
        problemWriteService.bulkInsertByProblemCommands(problemCommands);

        UserCommand userCommand = UserCommand.create(signupReq.getUsername(), signupReq.getPassword(), signupReq.getBaekjoonUsername());
        Long userId = userWriteService.save(userCommand);
    }
}
