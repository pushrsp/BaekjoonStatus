package project.BaekjoonStatus.api.auth.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class BaekjoonVerifyRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
}
