package project.BaekjoonStatus.shared.user.infra;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.user.domain.User;

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

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.username = user.getUsername();
        userEntity.baekjoonUsername = user.getBaekjoonUsername();
        userEntity.password = user.getPassword();
        userEntity.isPrivate = user.getIsPrivate();
        userEntity.createdTime = user.getCreatedTime();
        userEntity.modifiedTime = user.getModifiedTime();

        return userEntity;
    }

    public User to() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .baekjoonUsername(this.baekjoonUsername)
                .isPrivate(this.isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();

    }
}
