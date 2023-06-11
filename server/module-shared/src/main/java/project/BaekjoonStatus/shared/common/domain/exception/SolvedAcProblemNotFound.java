package project.BaekjoonStatus.shared.common.domain.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;

@Getter
@Setter
@NoArgsConstructor
public class SolvedAcProblemNotFound extends RuntimeException {
    private String code;
    private String message;

    public SolvedAcProblemNotFound(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }
}