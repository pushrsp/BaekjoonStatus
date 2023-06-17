package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.user.domain.User;

@Builder
@Data
public class MyProfileResponse {
    private Long id;
    private String username;

    public static MyProfileResponse from(User user) {
        return MyProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
