package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.member.domain.Member;

@Getter
public class MemberLoginResponse {
    private String id;
    private String username;
    private String token;

    @Builder
    private MemberLoginResponse(String id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public static MemberLoginResponse from(Member member, String token) {
        return MemberLoginResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .token(token)
                .build();
    }
}
