package project.BaekjoonStatus.shared.user.domain;

import lombok.Builder;
import lombok.Getter;

import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.common.utils.PasswordEncryptor;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String baekjoonUsername;
    private Boolean isPrivate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private User(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public void login(String username, String password, PasswordEncryptor passwordEncryptor) {
        if(!this.username.equals(username)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }

        if(!passwordEncryptor.validatePassword(password, this.password)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }
    }
}
