package project.BaekjoonStatus.shared.user.infra;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.common.domain.dto.UserDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

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

    private UserEntity(Long id, String username, String baekjoonUsername) {
        validateId(id);
        validateUsername(username);
        validateBaekjoonUsername(baekjoonUsername);

        this.id = id;
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
    }

    private UserEntity(String username, String baekjoonUsername, String password) {
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

    public static UserEntity of(String username, String baekjoonUsername, String password) {
        return new UserEntity(username, baekjoonUsername, password);
    }

    public static UserEntity from(UserDto userDto) {
        return new UserEntity(userDto.getUserId(), userDto.getUsername(), userDto.getBaekjoonUsername());
    }

    private void validateId(Long id) {
        Assert.notNull(id, "ID값을 입력해주세요.");
    }

    private void validateUsername(String username) {
        Assert.notNull(username, "아이디를 입력해주세요.");
        Assert.hasText(username, "아이디를 입력해주세요.");
    }

    private void validateBaekjoonUsername(String baekjoonUsername) {
        Assert.notNull(baekjoonUsername, "백준 아이디를 입력해주세요.");
        Assert.hasText(baekjoonUsername, "백준 아이디를 입력해주세요.");
    }

    private void validatePassword(String password) {
        Assert.notNull(password, "비밀번호를 입력해주세요.");
        Assert.hasText(password, "비밀번호를 입력해주세요.");
    }
}
