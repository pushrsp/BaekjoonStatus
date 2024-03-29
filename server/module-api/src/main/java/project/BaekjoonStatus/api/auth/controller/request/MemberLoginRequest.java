package project.BaekjoonStatus.api.auth.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.api.auth.service.request.MemberLoginServiceRequest;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private MemberLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MemberLoginServiceRequest toServiceRequest() {
        return MemberLoginServiceRequest.builder()
                .username(this.username)
                .password(this.password)
                .build();
    }
}
