package project.BaekjoonStatus.shared.domain.user.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(
                name = "username_unique",
                columnNames = "username"
        )
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", length = 50, nullable = false)
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

    public User(String username, String password, String baekjoonUsername, boolean isPrivate) {
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;

        ZoneId zoneId = ZoneId.of("UTC");

        this.createdTime = LocalDateTime.now(zoneId);
        if(this.modifiedTime == null)
            this.modifiedTime = LocalDateTime.now(zoneId);
    }
}
