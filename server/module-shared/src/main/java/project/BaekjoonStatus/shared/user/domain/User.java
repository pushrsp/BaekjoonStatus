package project.BaekjoonStatus.shared.user.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.domain.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.domain.exception.MyException;
import project.BaekjoonStatus.shared.common.utils.BcryptProvider;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.user.controller.request.UserCreateRequest;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final String baekjoonUsername;
    private final Boolean isPrivate;
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
