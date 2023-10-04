package project.BaekjoonStatus.api.auth.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.member.service.request.MemberCreateSharedServiceRequest;

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

    public MemberCreateSharedServiceRequest toRequest(Boolean isPrivate, PasswordService passwordService) {
        return MemberCreateSharedServiceRequest.builder()
                .username(this.username)
                .password(passwordService.hashPassword(this.password))
                .baekjoonUsername(this.baekjoonUsername)
                .isPrivate(isPrivate)
                .createdTime(this.createdTime)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
