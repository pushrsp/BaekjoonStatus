package project.BaekjoonStatus.api.auth.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserCreateServiceRequest {
    private String username;
    private String baekjoonUsername;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private UserCreateServiceRequest(String username, String baekjoonUsername, String password, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
        this.password = password;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public User toDomain(boolean isPrivate, PasswordEncryptor passwordEncryptor) {
        return User.builder()
                .username(this.username)
                .baekjoonUsername(this.baekjoonUsername)
                .password(passwordEncryptor.hashPassword(this.password))
                .isPrivate(isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
