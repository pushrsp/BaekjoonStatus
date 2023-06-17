package project.BaekjoonStatus.shared.common.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String code;
    private String message;
    private T data;

    public CommonResponse(CodeEnum codeEnum) {
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }

    public CommonResponse(T data) {
        setCode(CodeEnum.SUCCESS.getCode());
        setMessage(CodeEnum.SUCCESS.getMessage());
        setData(data);
    }

    public CommonResponse(CodeEnum codeEnum, T data) {
        setCode(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
        setData(data);
    }
}
