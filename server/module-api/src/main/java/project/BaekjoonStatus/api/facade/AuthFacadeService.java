package project.BaekjoonStatus.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.common.service.baekjoon.BaekjoonCrawling;
import project.BaekjoonStatus.shared.common.service.solvedac.SolvedAcHttp;
import project.BaekjoonStatus.shared.common.utils.BcryptProvider;
import project.BaekjoonStatus.shared.common.utils.JWTProvider;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.common.template.ListDividerTemplate;
import project.BaekjoonStatus.api.token.RegisterToken;
import project.BaekjoonStatus.api.token.RegisterTokenStore;
import project.BaekjoonStatus.shared.problem.service.ProblemService;
import project.BaekjoonStatus.shared.solvedhistory.infra.SolvedHistoryEntity;
import project.BaekjoonStatus.shared.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.tag.service.TagService;
import project.BaekjoonStatus.shared.user.infra.UserEntity;
import project.BaekjoonStatus.shared.user.service.UserService;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;

import java.util.*;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@Service
@RequiredArgsConstructor
public class AuthFacadeService {
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

    public LoginResp validateMe(String userId) {
        UserEntity user = userService.findById(Long.parseLong(userId))
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));

        return LoginResp.builder()
                .username(user.getUsername())
                .id(user.getId().toString())
                .build();
    }

    public ValidBaekjoonUsernameResp validateBaekjoonUsername(String username) {
        List<Long> problemIds = getProblemIds(username);
        return ValidBaekjoonUsernameResp.builder()
                .solvedCount(problemIds.size())
                .registerToken(registerTokenStore.put(problemIds))
                .build();
    }

    @Async
    public void createProblems(String registerTokenKey) {
        RegisterToken token = registerTokenStore.get(registerTokenKey);
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            List<Long> notSavedIds = problemService.findAllByNotExistedIds(ids);
            if(notSavedIds.isEmpty()) {
                return null;
            }

            List<SolvedAcProblemResponse> infos = SOLVED_AC_HTTP.getProblemsByProblemIds(notSavedIds);
            List<ProblemEntity> problems = problemService.saveAll(ProblemEntity.ofWithInfos(infos));
            tagService.saveAll(TagEntity.ofWithInfosAndProblems(infos, problems));

            return null;
        });
    }

    public CreateUserDto createUser(String registerToken, String username, String baekjoonUsername, String password) {
        validateExistedRegisterToken(registerToken);
        validateDuplicateUsername(username);

        UserEntity saveUser = userService.save(username, baekjoonUsername, BcryptProvider.hashPassword(password));
        return CreateUserDto.builder()
                .user(saveUser)
                .registerTokenKey(registerToken)
                .build();
    }

    @Async
    public void createSolvedHistories(CreateUserDto data) {
        RegisterToken token = registerTokenStore.get(data.getRegisterTokenKey());
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            solvedHistoryService.saveAll(SolvedHistoryEntity.ofWithUserAndProblems(data.getUser(), problemService.findAllByIdsIn(ids), true));
            return null;
        });

        registerTokenStore.remove(data.getRegisterTokenKey());
    }

    public LoginResp login(String username, String password) {
        UserEntity findUser = userService.findByUsername(username)
                .orElseThrow(() -> new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST));

        if(!BcryptProvider.validatePassword(password, findUser.getPassword())) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }

        return LoginResp.builder()
                .id(findUser.getId().toString())
                .username(findUser.getUsername())
                .token(JWTProvider.generateToken(findUser.getId().toString(), tokenSecret, EXPIRE_TIME))
                .build();
    }

    private void validateExistedRegisterToken(String registerToken) {
        if(!registerTokenStore.exist(registerToken)) {
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);
        }
    }

    private void validateDuplicateUsername(String username) {
        userService.findByUsername(username)
                .ifPresent((u) -> {
                    throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);
                });
    }

    private List<Long> getProblemIds(String username) {
        return new BaekjoonCrawling(username)
                .get();
    }
}
