package project.BaekjoonStatus.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.BaekjoonStatus.api.dto.AuthDto;
import project.BaekjoonStatus.api.service.AuthService;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("")
    public CommonResponse validUsername(@Valid AuthDto.ValidParams validParams) {
        authService.duplicateUsername(validParams.getUsername());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }

    @GetMapping("/baekjoon")
    public CommonResponse validBaekjoonUsername(@Valid AuthDto.ValidParams validParams) {
        AuthDto.SolvedCountResp solvedCountResp = authService.validBaekjoonUsername(validParams.getUsername());

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .data(solvedCountResp)
                .build();
    }

    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid AuthDto.SignupReq signupReq) {
        authService.create(signupReq);

        return CommonResponse.builder()
                .code(CodeEnum.SUCCESS.getCode())
                .message(CodeEnum.SUCCESS.getMessage())
                .build();
    }
}
