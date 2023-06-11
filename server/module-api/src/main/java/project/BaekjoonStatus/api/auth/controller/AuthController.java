package project.BaekjoonStatus.api.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.common.argumentresolver.Auth;
import project.BaekjoonStatus.api.auth.service.AuthService;
import project.BaekjoonStatus.shared.common.controller.response.CommonResponse;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;

import javax.validation.Valid;

import static project.BaekjoonStatus.api.auth.controller.request.AuthDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authFacadeService;

    @GetMapping("/me")
    public CommonResponse validateMe(@Auth String userId) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(authFacadeService.validateMe(userId))
                .build();
    }

    @GetMapping("/baekjoon")
    public CommonResponse validateBaekjoonUsername(@Valid ValidParams validParams) {
        ValidBaekjoonUsernameResp info = authFacadeService.validateBaekjoonUsername(validParams.getUsername());
        if(info.getSolvedCount() > 0)
            authFacadeService.createProblems(info.getRegisterToken());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(info)
                .build();
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid SignupReq body) {
        CreateUserDto info = authFacadeService.createUser(body.getRegisterToken(), body.getUsername(), body.getBaekjoonUsername(), body.getPassword());
        authFacadeService.createSolvedHistories(info);

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody @Valid LoginReq body) {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(authFacadeService.login(body.getUsername(), body.getPassword()))
                .build();
    }
}