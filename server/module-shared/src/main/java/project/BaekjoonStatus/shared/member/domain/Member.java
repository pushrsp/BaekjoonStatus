package project.BaekjoonStatus.shared.member.domain;

import lombok.Builder;
import lombok.Getter;

import project.BaekjoonStatus.shared.common.exception.CodeEnum;
import project.BaekjoonStatus.shared.common.exception.MyException;
import project.BaekjoonStatus.shared.common.service.PasswordService;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String username;
    private String password;
    private String baekjoonUsername;
    private Boolean isPrivate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private Member(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    //querydsl 전용 생성자
    public Member(Long id, String username, String baekjoonUsername) {
        this.id = id;
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
    }

    public void login(String username, String password, PasswordService passwordService) {
        if(!this.username.equals(username)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }

        if(!passwordService.validatePassword(password, this.password)) {
            throw new MyException(CodeEnum.MY_SERVER_LOGIN_BAD_REQUEST);
        }
    }
}
