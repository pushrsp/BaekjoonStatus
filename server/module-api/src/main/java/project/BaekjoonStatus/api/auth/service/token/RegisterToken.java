package project.BaekjoonStatus.api.auth.service.token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RegisterToken {
    private final LocalDate createdAt;
    private final List<String> problemIds;

    @Builder
    public RegisterToken(LocalDate createdAt, List<String> problemIds) {
        this.createdAt = createdAt;
        this.problemIds = problemIds;
    }
}
