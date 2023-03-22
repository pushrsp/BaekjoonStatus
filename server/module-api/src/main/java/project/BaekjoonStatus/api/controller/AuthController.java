package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.service.AuthService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

import java.util.Map;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/me")
    public CommonResponse validMe() {
        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/baekjoon")
    public CommonResponse validBaekjoonUsername(@Valid ValidParams validParams) {
        ValidBaekjoonUsernameResp info = authService.validBaekjoonUsername(validParams.getUsername());

        if(info.getSolvedHistories().size() > 0)
            authService.createSolvedProblems(info.getSolvedHistories());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(info)
                .build();
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid SignupReq body) {
        CreateUserDto info = authService.createUser(body);
        authService.createSolvedHistories(info);

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
                .data(authService.login(body))
                .build();
    }
}
