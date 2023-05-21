package project.BaekjoonStatus.shared.domain.user.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "baekjoon_username", length = 20, nullable = false)
    private String baekjoonUsername;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "created_time")
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    public User(UUID id, String username, String baekjoonUsername, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        this.createdTime = now;
        this.modifiedTime = now;
    }

    private User(String username,  String baekjoonUsername, String password) {
        validateUsername(username);
        validateBaekjoonUsername(baekjoonUsername);
        validatePassword(password);

        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        this.createdTime = now;
        this.modifiedTime = now;
    }

    public static User of(String username, String baekjoonUsername, String password) {
        return new User(username, baekjoonUsername, password);
    }

    private void validateUsername(String username) {
        Assert.notNull(username, "아이디를 입력해주세요.");
    }
    private void validateBaekjoonUsername(String baekjoonUsername) {
        Assert.notNull(baekjoonUsername, "백준 아이디를 입력해주세요.");
    }
    private void validatePassword(String password) {
        Assert.notNull(password, "비밀번호를 입력해주세요.");
    }
}
