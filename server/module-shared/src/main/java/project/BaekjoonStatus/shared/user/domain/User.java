package project.BaekjoonStatus.shared.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final String baekjoonUsername;
    private final boolean isPrivate;
    private final LocalDateTime createdTime;
    private final LocalDateTime modifiedTime;

    @Builder
    public User(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
