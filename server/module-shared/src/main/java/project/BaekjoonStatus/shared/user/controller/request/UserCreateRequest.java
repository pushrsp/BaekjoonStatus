package project.BaekjoonStatus.shared.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String registerToken;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
    private String username;

    @NotBlank(message = "백준 아이디를 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "백준 아이디는 특수문자를 제외한 2~20자리여야 합니다.")
    private String baekjoonUsername;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 6, max = 16, message = "최소 6자 최대 16자 이내여야 합니다.")
    private String password;
}
