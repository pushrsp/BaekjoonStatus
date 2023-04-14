package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.api.dto.AuthDto.LoginReq;
import project.BaekjoonStatus.api.dto.AuthDto.SignupReq;
import project.BaekjoonStatus.api.template.divider.ListDividerTemplate;
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
import java.util.concurrent.atomic.AtomicInteger;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final int OFFSET = 100;
    private static final Long EXPIRE_TIME = 1000L * 60 * 60 * 24; //하루
    private static final SolvedAcHttp SOLVED_AC_HTTP = new SolvedAcHttp();

    private final RegisterTokenStore registerTokenStore = new RegisterTokenStore();

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
        List<Long> problemIds = getProblemIds(username);
        return ValidBaekjoonUsernameResp.builder()
                .solvedCount(problemIds.size())
                .registerToken(registerTokenStore.put(problemIds))
                .build();
    }

    @Async
    @Transactional
    public void createProblems(String registerTokenKey) {
        RegisterToken token = registerTokenStore.get(registerTokenKey);
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            List<Long> saveIds = problemService.findProblemIdsByNotInWithLock(ids);
            if(saveIds.isEmpty())
                return null;

            List<SolvedAcProblemResp> infos = SOLVED_AC_HTTP.getProblemsByProblemIds(saveIds);
            List<Problem> problems = problemService.saveAll(infos);
            tagService.saveAll(infos, problems);

            return null;
        });
    }

    public CreateUserDto createUser(SignupReq data) {
        if(!registerTokenStore.exist(data.getRegisterToken()))
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        if(userService.exist(data.getUsername()))
            throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);

        User saveUser = userService.save(data.getUsername(), data.getBaekjoonUsername(), BcryptProvider.hashPassword(data.getPassword()));
        return CreateUserDto.builder()
                .user(saveUser)
                .registerTokenKey(data.getRegisterToken())
                .build();
    }

    @Async
    @Transactional
    public void createSolvedHistories(CreateUserDto data) {
        RegisterToken token = registerTokenStore.get(data.getRegisterTokenKey());
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            solvedHistoryService.saveAll(data.getUser(), problemService.findAllByIdsWithLock(ids), true);
            return null;
        });

        registerTokenStore.remove(data.getRegisterTokenKey());
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
                .token(JWTProvider.generateToken(findUser.get().getId().toString(), tokenSecret, EXPIRE_TIME))
                .build();
    }

    private List<Long> getProblemIds(String username) {
        return new BaekjoonCrawling(username)
                .getMySolvedHistories();
    }
}
