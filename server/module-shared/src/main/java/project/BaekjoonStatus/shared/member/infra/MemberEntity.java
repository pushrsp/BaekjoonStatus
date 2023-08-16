package project.BaekjoonStatus.shared.member.infra;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.BaekjoonStatus.shared.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "baekjoon_username", length = 20, nullable = false)
    private String baekjoonUsername;

    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate;

    @Column(name = "created_time")
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(name = "modified_time")
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    @Builder
    private MemberEntity(Long id, String username, String password, String baekjoonUsername, boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public static MemberEntity from(Member user) {
        return MemberEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .baekjoonUsername(user.getBaekjoonUsername())
                .isPrivate(user.getIsPrivate())
                .createdTime(user.getCreatedTime())
                .modifiedTime(user.getModifiedTime())
                .build();
    }

    public Member to() {
        return Member.builder()
                .id(String.valueOf(this.id))
                .username(this.username)
                .password(this.password)
                .baekjoonUsername(this.baekjoonUsername)
                .isPrivate(this.isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();

    }
}
