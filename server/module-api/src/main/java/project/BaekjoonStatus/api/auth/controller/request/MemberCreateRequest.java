package project.BaekjoonStatus.api.auth.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.api.auth.service.request.MemberCreateServiceRequest;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {
    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String registerToken;

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "백준 아이디를 입력해주세요.")
    private String baekjoonUsername;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private MemberCreateRequest(String registerToken, String username, String baekjoonUsername, String password) {
        this.registerToken = registerToken;
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
        this.password = password;
    }

    public MemberCreateServiceRequest toServiceRequest(LocalDateTime now) {
        return MemberCreateServiceRequest.builder()
                .username(this.username)
                .baekjoonUsername(this.baekjoonUsername)
                .password(this.password)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
