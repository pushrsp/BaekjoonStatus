package project.BaekjoonStatus.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.BaekjoonStatus.shared.dto.response.CommonResponse;
import project.BaekjoonStatus.shared.enums.CodeEnum;
import project.BaekjoonStatus.shared.exception.MyException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MyException.class)
    public CommonResponse myExceptionHandler(MyException e) {
        return CommonResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(BindException.class)
    public CommonResponse methodArgumentNotValidExceptionHandler(BindException e) {
        return CommonResponse.builder()
                .code(CodeEnum.USER_BAD_REQUEST.getCode())
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();
    }

//    @ExceptionHandler(Exception.class)
//    public CommonResponse exceptionHandler(Exception e) {
//        return CommonResponse.builder()
//                .code(CodeEnum.UNKNOWN_SERVER_ERROR.getCode())
//                .message(CodeEnum.UNKNOWN_SERVER_ERROR.getMessage())
//                .build();
//    }
}
