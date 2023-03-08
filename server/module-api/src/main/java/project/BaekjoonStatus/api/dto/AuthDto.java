package project.BaekjoonStatus.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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
    public static class SolvedCountResp {
        private long solvedCount;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupReq {
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        private String username;

        @NotBlank(message = "백준 아이디를 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "백준 아이디는 특수문자를 제외한 2~20자리여야 합니다.")
        private String baekjoonUsername;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

    }
}
