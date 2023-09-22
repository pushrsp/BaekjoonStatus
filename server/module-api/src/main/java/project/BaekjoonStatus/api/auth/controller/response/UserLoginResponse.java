package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.member.domain.Member;

@Getter
public class UserLoginResponse {
    private String id;
    private String username;
    private String token;

    @Builder
    private UserLoginResponse(String id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public static UserLoginResponse from(Member member, String token) {
        return UserLoginResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .token(token)
                .build();
    }
}
