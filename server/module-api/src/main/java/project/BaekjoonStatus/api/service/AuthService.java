package project.BaekjoonStatus.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.BaekjoonStatus.api.dto.AuthDto.LoginReq;
import project.BaekjoonStatus.api.dto.AuthDto.SignupReq;
import project.BaekjoonStatus.shared.application.CreateUserAndSolvedHistoryUsecase;
import project.BaekjoonStatus.shared.domain.user.service.UserReadService;
import project.BaekjoonStatus.shared.dto.command.CreateUserAndSolvedHistoryCommand;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;
import project.BaekjoonStatus.shared.util.BcryptProvider;
import project.BaekjoonStatus.shared.util.JWTProvider;

import java.net.URI;
import java.util.List;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private static final String CREATE_PROBLEMS="/problems";
    private final UserReadService userReadService;
    private final CreateUserAndSolvedHistoryUsecase createUserAndSolvedHistoryUsecase;
    private final JWTProvider jwtProvider;
    private final BcryptProvider bcryptProvider;

    @Value("${batch_url}")
    private String BATCH_URL;

    public void duplicateUsername(String username) {
        boolean isPresent = userReadService.existByUsername(username);
        if(isPresent)
            throw new MyException(CodeEnum.USER_DUPLICATE);
    }

    public SolvedCountResp validBaekjoonUsername(String username) {
        BaekjoonCrawling crawling = new BaekjoonCrawling(username);
        List<Long> solvedHistories = crawling.getMySolvedHistories();

        return SolvedCountResp.builder()
                .solvedCount(solvedHistories.size())
                .build();
    }

    @Async
    public void createSolvedProblems(String username) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(BATCH_URL)
                .path("/" + username + CREATE_PROBLEMS)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, null,CommonResponse.class);
    }

    @Transactional
    public void create(SignupReq signupReq) {
        List<Long> solvedHistories = getSolvedHistories(signupReq.getBaekjoonUsername());

        CreateUserAndSolvedHistoryCommand command = CreateUserAndSolvedHistoryCommand.builder()
                .username(signupReq.getUsername())
                .password(bcryptProvider.hashPassword(signupReq.getPassword()))
                .baekjoonUsername(signupReq.getBaekjoonUsername())
                .isBefore(true)
                .solvedHistories(solvedHistories)
                .build();

        createUserAndSolvedHistoryUsecase.execute(command);
    }

    public String login(LoginReq data) {
        return jwtProvider.generateToken(data.getUsername());
    }

    private List<Long> getSolvedHistories(String username) {
        return new BaekjoonCrawling(username)
                .getMySolvedHistories();
    }
}
