package project.BaekjoonStatus.api.auth.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginServiceRequest {
    private String username;
    private String password;

    @Builder
    private MemberLoginServiceRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // TODO: validation
    // @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,20}$", message = "아이디는 특수문자를 제외한 2~20자리여야 합니다.")
    // @Length(min = 6, max = 16, message = "최소 6자 최대 16자 이내여야 합니다.")
}
