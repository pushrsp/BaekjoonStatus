package project.BaekjoonStatus.shared.exception;

import lombok.Data;
import project.BaekjoonStatus.shared.enums.CodeEnum;

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
