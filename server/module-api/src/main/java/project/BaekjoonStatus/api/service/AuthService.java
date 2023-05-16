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
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemRepository;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemService;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryService;
import project.BaekjoonStatus.shared.domain.tag.repository.TagRepository;
import project.BaekjoonStatus.shared.domain.tag.service.TagService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserRepository;
import project.BaekjoonStatus.shared.domain.user.service.UserService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.*;

import java.util.*;

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

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final TagRepository tagRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    public LoginResp validateMe(String userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isEmpty())
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);

        return LoginResp.builder()
                .username(findUser.get().getUsername())
                .id(findUser.get().getId().toString())
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
    @Transactional
    public void createProblems(String registerTokenKey) {
        RegisterToken token = registerTokenStore.get(registerTokenKey);
        ListDividerTemplate<Long> listDivider = new ListDividerTemplate<>(OFFSET, token.getProblemIds());

        listDivider.execute((List<Long> ids) -> {
            List<Long> notSavedIds = problemRepository.findNotSavedProblemIds(ids);
            if(notSavedIds.isEmpty())
                return null;

            List<SolvedAcProblemResp> infos = SOLVED_AC_HTTP.getProblemsByProblemIds(notSavedIds);
            List<Problem> problems = problemRepository.saveAll(problemService.create(infos));
            tagRepository.saveAll(tagService.createWithInfosAndProblems(infos, problems));

            return null;
        });
    }

    public CreateUserDto createUser(SignupReq data) {
        if(!registerTokenStore.exist(data.getRegisterToken()))
            throw new MyException(CodeEnum.MY_SERVER_UNAUTHORIZED);

        if(userRepository.existByUsername(data.getUsername()))
            throw new MyException(CodeEnum.MY_SERVER_DUPLICATE);

        User saveUser = userRepository.save(userService.create(data.getUsername(), data.getBaekjoonUsername(), BcryptProvider.hashPassword(data.getPassword())));
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
            solvedHistoryRepository.saveAll(solvedHistoryService.createWithProblems(data.getUser(), problemRepository.findAllByIdsWithLock(ids), true));
            return null;
        });

        registerTokenStore.remove(data.getRegisterTokenKey());
    }

    public LoginResp login(LoginReq data) {
        User findUser = userService.validate(userRepository.findByUsername(data.getUsername()), data.getPassword());

        return LoginResp.builder()
                .id(findUser.getId().toString())
                .username(findUser.getUsername())
                .token(JWTProvider.generateToken(findUser.getId().toString(), tokenSecret, EXPIRE_TIME))
                .build();
    }

    private List<Long> getProblemIds(String username) {
        return new BaekjoonCrawling(username)
                .get();
    }
}
