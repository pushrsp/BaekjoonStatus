package project.BaekjoonStatus.api.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.auth.controller.request.BaekjoonVerifyRequest;
import project.BaekjoonStatus.api.auth.controller.request.UserLoginRequest;
import project.BaekjoonStatus.api.auth.controller.response.UserLoginResponse;
import project.BaekjoonStatus.shared.user.controller.request.UserCreateRequest;
import project.BaekjoonStatus.api.auth.controller.response.BaekjoonVerifyResponse;
import project.BaekjoonStatus.api.auth.controller.response.MyProfileResponse;
import project.BaekjoonStatus.api.common.argumentresolver.Auth;
import project.BaekjoonStatus.api.auth.service.AuthService;
import project.BaekjoonStatus.shared.common.controller.response.CommonResponse;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.user.domain.User;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private static final Long EXPIRE_TIME = 1000L * 60 * 60 * 24; //하루

    private final AuthService authService;

    @Value("${token.secret}")
    private String tokenSecret;

    @GetMapping("/me")
    public CommonResponse get(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(MyProfileResponse.from(authService.getById(userId)))
                .build();
    }

    @GetMapping("/baekjoon")
    public CommonResponse verifyBaekjoon(@Valid BaekjoonVerifyRequest request) {
        List<Long> problemIds = authService.findByBaekjoonUsername(request.getUsername());
        String registerToken = authService.getRegisterToken(problemIds);

        if(problemIds.size() > 0) {
            authService.createProblems(registerToken);
        }

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(BaekjoonVerifyResponse.from(problemIds, registerToken))
                .build();
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid UserCreateRequest request) {
        authService.verifyRegisterToken(request.getRegisterToken());

        User user = authService.createUser(request);
        authService.createSolvedHistories(user, request.getRegisterToken());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody @Valid UserLoginRequest request) {
        User user = authService.login(request);
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(UserLoginResponse.from(user, tokenSecret, EXPIRE_TIME))
                .build();
    }
}
