package project.BaekjoonStatus.shared.dto.command;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCommand {
    private String username;
    private String password;
    private String baekjoonUsername;
    private boolean isPrivate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    private UserCommand(String username, String password, String baekjoonUsername) {
        //TODO bcrypt 비밀번호 생성
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
    }

    private UserCommand(String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public static UserCommand create(String username, String password, String baekjoonUsername) {
        return new UserCommand(username, password, baekjoonUsername);
    }

    public static UserCommand create(String username, String password, String baekjoonUsername,  boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        return new UserCommand(username, password, baekjoonUsername, isPrivate, createdTime, modifiedTime);
    }
}
