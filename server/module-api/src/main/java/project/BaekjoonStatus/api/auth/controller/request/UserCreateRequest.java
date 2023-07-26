package project.BaekjoonStatus.api.auth.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.api.auth.service.request.UserCreateServiceRequest;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String registerToken;

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "백준 아이디를 입력해주세요.")
    private String baekjoonUsername;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public UserCreateServiceRequest toServiceRequest(LocalDateTime now) {
        return UserCreateServiceRequest.builder()
                .username(this.username)
                .baekjoonUsername(this.baekjoonUsername)
                .password(this.password)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
