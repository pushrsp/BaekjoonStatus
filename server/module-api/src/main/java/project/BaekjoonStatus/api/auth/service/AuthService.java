package project.BaekjoonStatus.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.api.auth.controller.request.UserLoginRequest;
import project.BaekjoonStatus.shared.user.controller.request.UserCreateRequest;
import project.BaekjoonStatus.shared.baekjoon.BaekjoonService;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.common.template.ListDividerTemplate;
import project.BaekjoonStatus.api.auth.service.token.RegisterToken;
import project.BaekjoonStatus.api.auth.service.token.RegisterTokenStore;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.UserService;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final int OFFSET = 100;

    private final RegisterTokenStore registerTokenStore = new RegisterTokenStore();

    private final BaekjoonService baekjoonService;
    private final SolvedAcService solvedAcService;

    private final ProblemService problemService;
    private final TagService tagService;
    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;

    public User getById(String userId) {
        return userService.findById(Long.parseLong(userId))
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));
    }

    public List<Long> findByBaekjoonUsername(String baekjoonUsername) {
        return baekjoonService.findByUsername(baekjoonUsername);
    }

    public String getRegisterToken(List<Long> problemIds) {
        return registerTokenStore.put(problemIds);
    }

    @Async
    public void createProblems(String registerToken) {
        RegisterToken token = registerTokenStore.get(registerToken);
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            List<Long> notSavedIds = problemService.findAllByNotExistedIds(ids);
            if(notSavedIds.isEmpty()) {
                return null;
            }

            List<SolvedAcProblem> solvedAcProblems = solvedAcService.findByIds(notSavedIds);
            problemService.saveAll(SolvedAcProblem.toProblemList(solvedAcProblems));
            tagService.saveAll(SolvedAcProblem.toTagList(solvedAcProblems));

            return null;
        });
    }

    public User createUser(UserCreateRequest userCreate) {
        duplicateUsername(userCreate.getUsername());

        return userService.save(User.from(userCreate));
    }

    @Async
    public void createSolvedHistories(User user, String registerToken) {
        RegisterToken token = registerTokenStore.get(registerToken);
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        //FIXME
//        listDivider.execute((List<Long> ids) -> {
//            solvedHistoryService.saveAll(SolvedHistoryEntity.ofWithUserAndProblems(user, problemService.findAllByIdsIn(ids), true));
//            return null;
//        });

        registerTokenStore.remove(registerToken);
    }

    public User login(UserLoginRequest userLogin) {
        User findUser = userService.findByUsername(userLogin.getUsername())
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));

        findUser.login(userLogin.getUsername(), userLogin.getPassword());

        return findUser;
    }

    public void verifyRegisterToken(String registerToken) {
        if(!registerTokenStore.exist(registerToken)) {
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);
        }
    }

    private void duplicateUsername(String username) {
        userService.findByUsername(username).ifPresent((u) -> {
                    throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);
                });
    }
}
