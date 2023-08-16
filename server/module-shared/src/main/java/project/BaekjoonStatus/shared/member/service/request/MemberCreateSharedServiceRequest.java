package project.BaekjoonStatus.shared.member.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public class MemberCreateSharedServiceRequest {
    private String username;
    private String password;
    private String baekjoonUsername;
    private Boolean isPrivate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private MemberCreateSharedServiceRequest(String username, String password, String baekjoonUsername, Boolean isPrivate, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.password = password;
        this.baekjoonUsername = baekjoonUsername;
        this.isPrivate = isPrivate;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public Member toDomain() {
        return Member.builder()
                .username(this.username)
                .password(this.password)
                .baekjoonUsername(this.baekjoonUsername)
                .isPrivate(this.isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
