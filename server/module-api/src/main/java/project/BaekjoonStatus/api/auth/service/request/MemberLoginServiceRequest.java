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
}
