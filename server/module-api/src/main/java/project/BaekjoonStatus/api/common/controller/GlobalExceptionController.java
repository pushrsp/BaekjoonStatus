package project.BaekjoonStatus.api.common.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.BaekjoonStatus.shared.common.response.CommonResponse;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {
    @ExceptionHandler(MyException.class)
    public CommonResponse myExceptionHandler(MyException e) {
        return CommonResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    public CommonResponse tokenExpiredExceptionHandler(TokenExpiredException e) {
        return CommonResponse.builder()
                .code(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getCode())
                .message(CodeEnum.MY_SERVER_TOKEN_EXPIRED.getMessage())
                .build();
    }

    @ExceptionHandler(JWTDecodeException.class)
    public CommonResponse jwtDecodeExceptionHandler(JWTDecodeException e) {
        return CommonResponse.builder()
                .code(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getCode())
                .message(CodeEnum.MY_SERVER_NOT_VALID_TOKEN.getMessage())
                .build();
    }

    @ExceptionHandler(BindException.class)
    public CommonResponse methodArgumentNotValidExceptionHandler(BindException e) {
        return CommonResponse.builder()
                .code(CodeEnum.MY_SERVER_DTO_BAD_REQUEST.getCode())
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHandler(Exception e) {
        return CommonResponse.builder()
                .code(CodeEnum.UNKNOWN_EXCEPTION.getCode())
                .message(CodeEnum.UNKNOWN_EXCEPTION.getMessage())
                .build();
    }
}
