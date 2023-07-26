package project.BaekjoonStatus.shared.user.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserCreateServiceDto {
    private String username;
    private String baekjoonUsername;
    private String password;
    private Boolean isPrivate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private UserCreateServiceDto(String username, String baekjoonUsername, String password, Boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
        this.password = password;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public User toDomain() {
        return User.builder()
                .username(this.username)
                .baekjoonUsername(this.baekjoonUsername)
                .password(this.password)
                .isPrivate(this.isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
