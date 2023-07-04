package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SolvedHistory {
    private final String id;
    private final User user;
    private final Problem problem;
    private final Boolean isBefore;
    private final Integer problemLevel;
    private final LocalDate createdDate;
    private final LocalDateTime createdTime;

    @Builder
    public SolvedHistory(String id, User user, Problem problem, Boolean isBefore, Integer problemLevel, LocalDate createdDate, LocalDateTime createdTime) {
        this.id = id;
        this.user = user;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problemLevel;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    public static SolvedHistory from(User user, Problem problem, boolean isBefore, LocalDateTime createdTime, LocalDate createdDate) {
        return SolvedHistory.builder()
                .user(user)
                .problem(problem)
                .isBefore(isBefore)
                .problemLevel(problem.getLevel())
                .createdTime(createdTime)
                .createdDate(createdDate)
                .build();
    }

    public static List<SolvedHistory> from(User user, List<Problem> problems, boolean isBefore) {
        LocalDateTime ct = DateProvider.getDateTime();
        LocalDate cd = DateProvider.getDate();

        return problems.stream()
                .map(p -> SolvedHistory.from(user, p, isBefore, ct, cd))
                .collect(Collectors.toList());
    }
}
