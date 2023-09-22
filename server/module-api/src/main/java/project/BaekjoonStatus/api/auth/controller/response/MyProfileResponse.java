package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.member.domain.Member;

@Getter
public class MyProfileResponse {
    private String id;
    private String username;

    @Builder
    private MyProfileResponse(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public static MyProfileResponse from(Member member) {
        return MyProfileResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }
}
