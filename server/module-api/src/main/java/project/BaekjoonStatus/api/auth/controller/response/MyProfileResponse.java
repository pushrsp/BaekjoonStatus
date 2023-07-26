package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.user.domain.User;

@Getter
public class MyProfileResponse {
    private Long id;
    private String username;

    @Builder
    private MyProfileResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static MyProfileResponse from(User user) {
        return MyProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
