package project.BaekjoonStatus.api.auth.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public class MemberCreateServiceRequest {
    private String username;
    private String baekjoonUsername;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private MemberCreateServiceRequest(String username, String baekjoonUsername, String password, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
        this.password = password;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public Member toDomain(boolean isPrivate, PasswordService passwordService) {
        return Member.builder()
                .username(this.username)
                .baekjoonUsername(this.baekjoonUsername)
                .password(passwordService.hashPassword(this.password))
                .isPrivate(isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
