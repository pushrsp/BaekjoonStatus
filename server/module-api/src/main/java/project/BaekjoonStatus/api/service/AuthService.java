package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.api.dto.AuthDto;
import project.BaekjoonStatus.shared.application.CreateProblemsAndTagsUsecase;
import project.BaekjoonStatus.shared.application.CreateUserAndSolvedHistoryUsecase;
import project.BaekjoonStatus.shared.domain.user.service.UserReadService;
import project.BaekjoonStatus.shared.dto.command.CreateProblemsAndTagsCommand;
import project.BaekjoonStatus.shared.dto.command.CreateUserAndSolvedHistoryCommand;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.dto.response.SolvedAcUserResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserReadService userReadService;
    private final CreateUserAndSolvedHistoryUsecase createUserAndSolvedHistoryUsecase;
    private final CreateProblemsAndTagsUsecase createProblemsAndTagsUsecase;

    public void duplicateUsername(String username) {
        boolean isPresent = userReadService.existByUsername(username);
        if(isPresent)
            throw new MyException(CodeEnum.USER_DUPLICATE);
    }

    @Transactional
    public AuthDto.SolvedCountResp validBaekjoonUsername(String username) {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();
        SolvedAcUserResp info = solvedAcHttp.getBaekjoonUser(username);
        if(info == null)
            throw new MyException(CodeEnum.SOLVED_AC_USER_NOT_FOUND);

        List<Long> solvedHistories = getSolvedHistories(username);
        List<SolvedAcProblemResp> problemInfos = solvedAcHttp.getProblemsByProblemIds(solvedHistories);

        CreateProblemsAndTagsCommand command = CreateProblemsAndTagsCommand.builder()
                .problemInfos(problemInfos)
                .build();

        return new AuthDto.SolvedCountResp(createProblemsAndTagsUsecase.execute(command));
    }

    @Transactional
    public void create(AuthDto.SignupReq signupReq) {
        List<Long> solvedHistories = getSolvedHistories(signupReq.getUsername());

        CreateUserAndSolvedHistoryCommand command = CreateUserAndSolvedHistoryCommand.builder()
                .username(signupReq.getUsername())
                .password(signupReq.getPassword())
                .baekjoonUsername(signupReq.getBaekjoonUsername())
                .isBefore(true)
                .solvedHistories(solvedHistories)
                .build();

        createUserAndSolvedHistoryUsecase.execute(command);
    }

    private List<Long> getSolvedHistories(String username) {
        return new BaekjoonCrawling(username)
                .getMySolvedHistories();
    }
}
