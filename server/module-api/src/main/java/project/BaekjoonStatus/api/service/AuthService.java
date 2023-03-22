package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.api.dto.AuthDto.LoginReq;
import project.BaekjoonStatus.api.dto.AuthDto.SignupReq;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.problemtag.service.ProblemTagService;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.BcryptProvider;
import project.BaekjoonStatus.shared.util.JWTProvider;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private static final int PROBLEM_ID_OFFSET = 100;
    private final ProblemService problemService;
    private final ProblemTagService problemTagService;
    private final TagService tagService;
    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;
    private final JWTProvider jwtProvider;
    private final BcryptProvider bcryptProvider;

    public SolvedCountResp validBaekjoonUsername(String username) {
        return SolvedCountResp.builder()
                .solvedHistories(getSolvedHistories(username))
                .build();
    }

    @Transactional
    public User createUser(SignupReq data) {
        if(userService.exist(data.getUsername()))
            throw new MyException(CodeEnum.USER_DUPLICATE);

        return userService.save(data.getUsername(), data.getBaekjoonUsername(), bcryptProvider.hashPassword(data.getPassword()));
    }

    @Async
    @Transactional
    public void createSolvedHistories(User user) {
        List<Long> problemIds = getSolvedHistories(user.getBaekjoonUsername());

        int startIndex = 0;
        while (startIndex < problemIds.size()) {
            List<Long> ids = problemIds.subList(startIndex, Math.min(startIndex + PROBLEM_ID_OFFSET, problemIds.size()));
            solvedHistoryService.saveAll(user, problemService.findByIds(ids), true);

            startIndex += PROBLEM_ID_OFFSET;
        }
    }

    @Async
    @Transactional
    public void createSolvedProblems(List<Long> problemIds) {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();

        int startIndex = 0;
        while (startIndex < problemIds.size()) {
            List<Long> ids = problemIds.subList(startIndex, Math.min(startIndex + PROBLEM_ID_OFFSET, problemIds.size()));
            List<Long> saveIds = problemService.findProblemIdsByNotInclude(ids);

            List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(saveIds);
            List<Problem> problems = problemService.saveAll(infos);
            List<Tag> tags = tagService.saveAllByNotIn(getTagNames(infos));
            problemTagService.saveAllByProblemInfos(infos, problems, tags);

            startIndex += PROBLEM_ID_OFFSET;
        }
    }

    private Set<String> getTagNames(List<SolvedAcProblemResp> infos) {
        Set<String> ret = new HashSet<>();
        for (SolvedAcProblemResp info : infos) {
            for (SolvedAcProblemResp.Tag tag : info.getTags())
                ret.add(tag.getKey());
        }

        return ret;
    }

    public String login(LoginReq data) {
        return jwtProvider.generateToken(data.getUsername());
    }

    private List<Long> getSolvedHistories(String username) {
        return new BaekjoonCrawling(username)
                .getMySolvedHistories();
    }
}
