package project.BaekjoonStatus.api.auth.controller;

import lombok.RequiredArgsConstructor;
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
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.user.domain.User;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final int OFFSET = 100;
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
        List<Long> problemIds = authService.getByBaekjoonUsername(request.getUsername());
        String registerToken = authService.getRegisterToken(problemIds);

        createProblems(problemIds);

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(BaekjoonVerifyResponse.from(problemIds, registerToken))
                .build();
    }

    private void createProblems(List<Long> problemIds) {
        int start = 0;
        while (start < problemIds.size()) {
            authService.createProblems(problemIds.subList(start, Math.min(start + OFFSET, problemIds.size())));
            start += OFFSET;
        }
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid UserCreateRequest request) {
        authService.verifyRegisterToken(request.getRegisterToken());

        User user = authService.createUser(request);
        createSolvedHistories(user, authService.getProblemIds(request.getRegisterToken()));

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    private void createSolvedHistories(User user, List<Long> problemIds) {
        int start = 0;
        while (start < problemIds.size()) {
            authService.createSolvedHistories(user, problemIds.subList(start, Math.min(start + OFFSET, problemIds.size())));
            start += OFFSET;
        }
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
