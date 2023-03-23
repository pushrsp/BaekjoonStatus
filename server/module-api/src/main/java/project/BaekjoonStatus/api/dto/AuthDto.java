package project.BaekjoonStatus.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import project.BaekjoonStatus.shared.domain.user.entity.User;

import javax.validation.constraints.*;
import java.util.List;

public class AuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidParams {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        private String username;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ValidBaekjoonUsernameResp {
        @JsonIgnore
        private List<Long> solvedHistories;
        private int solvedCount;
        private String registerToken;
    }

    @Getter
    @Builder
    public static class CreateUserDto {
        private User user;
        private String registerTokenKey;
    }

    @Getter
    @Builder
    public static class LoginResp {
        private String id;
        private String username;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String token;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupReq {
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginReq {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Length(min = 6, max = 16, message = "최소 6자 최대 16자 이내여야 합니다.")
        private String password;
    }
}
