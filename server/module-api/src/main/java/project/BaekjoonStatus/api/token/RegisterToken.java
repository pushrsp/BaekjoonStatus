package project.BaekjoonStatus.api.token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RegisterToken {
    private final LocalDate createdAt;
    private final List<Long> problemIds;

    @Builder
    public RegisterToken(LocalDate createdAt, List<Long> problemIds) {
        this.createdAt = createdAt;
        this.problemIds = problemIds;
    }
}
