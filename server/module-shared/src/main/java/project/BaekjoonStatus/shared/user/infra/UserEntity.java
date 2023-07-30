package project.BaekjoonStatus.shared.user.infra;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Builder
    private UserEntity(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .baekjoonUsername(user.getBaekjoonUsername())
                .isPrivate(user.getIsPrivate())
                .createdTime(user.getCreatedTime())
                .modifiedTime(user.getModifiedTime())
                .build();
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
