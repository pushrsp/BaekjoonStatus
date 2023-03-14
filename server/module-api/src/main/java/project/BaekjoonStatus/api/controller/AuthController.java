package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.dto.AuthDto;
import project.BaekjoonStatus.api.service.AuthService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

import static project.BaekjoonStatus.api.dto.AuthDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("")
    public CommonResponse validUsername(@Valid ValidParams validParams) {
        authService.duplicateUsername(validParams.getUsername());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/baekjoon")
    public CommonResponse validBaekjoonUsername(@Valid ValidParams validParams) throws InterruptedException {
        authService.createSolvedProblems(validParams.getUsername());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(authService.validBaekjoonUsername(validParams.getUsername()))
                .build();
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid SignupReq body) {
        authService.create(body);

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
