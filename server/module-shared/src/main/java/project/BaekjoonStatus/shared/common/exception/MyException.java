package project.BaekjoonStatus.shared.common.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException {
    private String code;
    private String message;

    public MyException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }
}
