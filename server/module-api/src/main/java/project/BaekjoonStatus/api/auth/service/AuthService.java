package project.BaekjoonStatus.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

import project.BaekjoonStatus.api.auth.service.request.UserCreateServiceRequest;
import project.BaekjoonStatus.api.auth.service.request.UserLoginServiceRequest;
import project.BaekjoonStatus.api.auth.service.token.RegisterTokenStore;
import project.BaekjoonStatus.api.concurrent.annotation.CustomAsync;

import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.baekjoon.BaekjoonService;
import project.BaekjoonStatus.shared.solvedac.domain.SolvedAcProblem;
import project.BaekjoonStatus.shared.solvedac.service.SolvedAcService;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.UserService;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RegisterTokenStore registerTokenStore = new RegisterTokenStore();

    private final BaekjoonService baekjoonService;
    private final SolvedAcService solvedAcService;

    private final ProblemService problemService;
    private final TagService tagService;
    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;

    private final PasswordEncryptor passwordEncryptor;

    public User getById(String userId) {
        return userService.findById(Long.parseLong(userId))
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));
    }

    public List<Long> getByBaekjoonUsername(String baekjoonUsername) {
        // TODO: validation
        // @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        return baekjoonService.getProblemIdsByUsername (baekjoonUsername);
    }

    public String getRegisterToken(List<Long> problemIds) {
        return registerTokenStore.put(problemIds);
    }

    @CustomAsync(maxTry = 10, offset = 2, delay = 180000)
    public void createProblems(List<Long> problemIds, LocalDateTime createdTime) {
        List<Long> notSavedIds = problemService.findAllByNotExistedIds(problemIds);
        if(notSavedIds.isEmpty()) {
            return;
        }

        List<SolvedAcProblem> solvedAcProblems = solvedAcService.findByIds(notSavedIds);
        problemService.saveAll(SolvedAcProblem.toProblemList(solvedAcProblems, createdTime));
        tagService.saveAll(SolvedAcProblem.toTagList(solvedAcProblems, createdTime));
    }

    @Recover
    private void recover(MyException e, List<Long> ids, int index) {
        //FIXME
        log.info("createProblems index: {}", index);
    }

    @CustomAsync(maxTry = 10, offset = 2, delay = 200000)
    public void createSolvedHistories(User user, List<Long> problemIds) {
        List<Problem> problems = problemService.findAllByIdsIn(problemIds);
        if(problems.size() != problemIds.size()) {
            throw new MyException(CodeEnum.SOLVED_AC_SERVER_ERROR);
        }

        solvedHistoryService.saveAll(SolvedHistory.from(user, problems,true));
    }

    @Recover
    public void recover(MyException e, User user, List<Long> problemIds) {
        //FIXME
        log.info("createSolvedHistories userId: {}", user.getId());
    }

    public User createUser(UserCreateServiceRequest request) {
        duplicateUsername(request.getUsername());

        return userService.save(request.toServiceDto(true, passwordEncryptor));
    }

    public List<Long> getProblemIds(String registerToken) {
        return registerTokenStore.get(registerToken).getProblemIds();
    }

    public User login(UserLoginServiceRequest request) {
        User findUser = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));

        findUser.login(request.getUsername(), request.getPassword(), passwordEncryptor);

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
