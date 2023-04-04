package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.api.dto.AuthDto.LoginReq;
import project.BaekjoonStatus.api.dto.AuthDto.SignupReq;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.*;

import java.util.*;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private static final int PROBLEM_ID_OFFSET = 100;
    private final HashMap<String, RegisterToken> registerTokenStore = new HashMap<>();

    @Value("${token.secret}")
    private String tokenSecret;

    private final ProblemService problemService;
    private final TagService tagService;
    private final UserService userService;
    private final SolvedHistoryService solvedHistoryService;

    public LoginResp validMe(String userId) {
        Optional<User> findUser = userService.findById(userId);
        if(findUser.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        return LoginResp.builder()
                .username(findUser.get().getUsername())
                .id(findUser.get().getId().toString())
                .build();
    }

    public ValidBaekjoonUsernameResp validBaekjoonUsername(String username) {
        List<Long> solvedHistories = getSolvedHistories(username);
        RegisterToken token = RegisterToken.builder()
                .createdAt(DateProvider.getDate())
                .solvedHistories(solvedHistories)
                .build();

        String key = UUID.randomUUID().toString();
        registerTokenStore.put(key, token);

        return ValidBaekjoonUsernameResp.builder()
                .solvedHistories(solvedHistories)
                .solvedCount(solvedHistories.size())
                .registerToken(key)
                .build();
    }

    @Transactional
    public CreateUserDto createUser(SignupReq data) {
        if(registerTokenStore.get(data.getRegisterToken()) == null)
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        if(userService.exist(data.getUsername()))
            throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);

        User saveUser = userService.save(data.getUsername(), data.getBaekjoonUsername(), BcryptProvider.hashPassword(data.getPassword()));
        return CreateUserDto.builder()
                .user(saveUser)
                .registerTokenKey(data.getRegisterToken())
                .build();
    }

    public LoginResp login(LoginReq data) {
        Optional<User> findUser = userService.findByUsername(data.getUsername());
        if(findUser.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        if(!BcryptProvider.validPassword(data.getPassword(), findUser.get().getPassword()))
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        return LoginResp.builder()
                .id(findUser.get().getId().toString())
                .username(findUser.get().getUsername())
                .token(JWTProvider.generateToken(findUser.get().getId().toString(), tokenSecret))
                .build();
    }

    @Async
    @Transactional
    public void createSolvedHistories(CreateUserDto data) {
        List<Long> problemIds = getProblemIds(data.getRegisterTokenKey());

        int startIndex = 0;
        while (startIndex < problemIds.size()) {
            List<Long> ids = problemIds.subList(startIndex, Math.min(startIndex + PROBLEM_ID_OFFSET, problemIds.size()));
            startIndex += PROBLEM_ID_OFFSET;

            solvedHistoryService.saveAll(data.getUser(), problemService.findAllByIdsWithLock(ids), true);
        }
    }

    @Async
    @Transactional
    public void createSolvedProblems(List<Long> problemIds) {
        SolvedAcHttp solvedAcHttp = new SolvedAcHttp();

        int startIndex = 0;
        while (startIndex < problemIds.size()) {
            List<Long> ids = problemIds.subList(startIndex, Math.min(startIndex + PROBLEM_ID_OFFSET, problemIds.size()));
            startIndex += PROBLEM_ID_OFFSET;

            List<Long> saveIds = problemService.findProblemIdsByNotInWithLock(ids);
            if(saveIds.isEmpty())
                continue;

            List<SolvedAcProblemResp> infos = solvedAcHttp.getProblemsByProblemIds(saveIds);
            List<Problem> problems = problemService.saveAll(infos);
            tagService.saveAll(infos, problems);
        }
    }

    private List<Long> getProblemIds(String tokenKey) {
        RegisterToken registerToken = registerTokenStore.get(tokenKey);
        registerTokenStore.remove(tokenKey);

        return registerToken.getSolvedHistories();
    }

    private List<Long> getSolvedHistories(String username) {
        return new BaekjoonCrawling(username)
                .getMySolvedHistories();
    }
}
