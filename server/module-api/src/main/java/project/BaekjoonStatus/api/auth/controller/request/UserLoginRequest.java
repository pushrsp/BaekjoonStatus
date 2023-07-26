package project.BaekjoonStatus.api.auth.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.api.auth.service.request.UserLoginServiceRequest;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserLoginServiceRequest toServiceRequest() {
        return UserLoginServiceRequest.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}
