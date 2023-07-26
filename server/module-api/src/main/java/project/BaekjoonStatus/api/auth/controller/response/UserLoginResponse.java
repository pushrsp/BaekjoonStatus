package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.utils.JWTProvider;
import project.BaekjoonStatus.shared.user.domain.User;

@Getter
public class UserLoginResponse {
    private Long id;
    private String username;
    private String token;

    @Builder
    private UserLoginResponse(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }

    public static UserLoginResponse from(User user, String secret, long expiredTime) {
        return UserLoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(JWTProvider.generateToken(user.getId().toString(), secret, expiredTime))
                .build();
    }
}
