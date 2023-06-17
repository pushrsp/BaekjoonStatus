package project.BaekjoonStatus.shared.user.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.common.utils.BcryptProvider;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.user.controller.request.UserCreateRequest;

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
    public User(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public User(Long userId, String username, String baekjoonUsername) {
        this.id = userId;
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
    }

    public static User from(UserCreateRequest userCreate) {
        return User.builder()
                .username(userCreate.getUsername())
                .baekjoonUsername(userCreate.getBaekjoonUsername())
                .password(BcryptProvider.hashPassword(userCreate.getPassword()))
                .isPrivate(true)
                .createdTime(DateProvider.getDateTime())
                .modifiedTime(DateProvider.getDateTime())
                .build();
    }

    public void login(String username, String password) {
        if(!this.username.equals(username)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }

        if(!BcryptProvider.validatePassword(password, this.password)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }
    }
}
