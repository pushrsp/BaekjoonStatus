package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SolvedHistory {
    private final String id;
    private final User user;
    private final Problem problem;
    private final boolean isBefore;
    private final Integer problemLevel;
    private final LocalDate createdDate;
    private final LocalDateTime createdTime;

    @Builder
    public SolvedHistory(String id, User user, Problem problem, boolean isBefore, Integer problemLevel, LocalDate createdDate, LocalDateTime createdTime) {
        this.id = id;
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problemLevel;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }
}
