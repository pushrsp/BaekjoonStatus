package project.BaekjoonStatus.api.auth.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.member.service.request.MemberCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Getter
public class MemberCreateServiceRequest {
    private static final String REGEX = "^[a-z0-9]+$";

    private final String username;
    private final String baekjoonUsername;
    private final String password;
    private final LocalDateTime createdTime;
    private final LocalDateTime modifiedTime;

    @Builder
    private MemberCreateServiceRequest(String username, String baekjoonUsername, String password, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.username = username;
        this.baekjoonUsername = baekjoonUsername;
        this.password = password;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    private boolean isValidUsername(String username) {
        return Pattern.matches(REGEX, username);
    }

    private boolean isValidLength(int min, int max, String str) {
        return min <= str.length() && str.length() <= max;
    }

    private void validateUsername() {
        if(!isValidUsername(this.username)) {
            throw new IllegalStateException("아이디는 영어와 숫자로만 이루어져야 합니다.");
        }

        if(!isValidLength(5, 15, this.username)) {
            throw new IllegalStateException("최소 5자 최대 15자 이내여야 합니다.");
        }
    }

    private void validatePassword() {
        if(!isValidLength(8, 20, this.password)) {
            throw new IllegalStateException("최소 8자 최대 20자 이내여야 합니다.");
        }
    }

    public MemberCreateSharedServiceRequest toRequest(Boolean isPrivate, PasswordService passwordService) {
        validateUsername();
        validatePassword();

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
