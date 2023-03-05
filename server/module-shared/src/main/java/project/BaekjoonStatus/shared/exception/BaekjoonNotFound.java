package project.BaekjoonStatus.shared.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.BaekjoonStatus.shared.enums.CodeEnum;

@Getter
@Setter
@NoArgsConstructor
public class BaekjoonNotFound extends RuntimeException {
    private String code;
    private String message;

    public BaekjoonNotFound(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }
}
