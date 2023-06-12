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

    public static List<SolvedHistory> from(User user, List<Problem> problems, boolean isBefore) {
        List<SolvedHistory> ret = new ArrayList<>();
        LocalDateTime ct = DateProvider.getDateTime();
        LocalDate cd = DateProvider.getDate();

        for (Problem problem : problems) {
            ret.add(SolvedHistory.builder()
                            .user(user)
                            .problem(problem)
                            .isBefore(isBefore)
                            .problemLevel(problem.getLevel())
                            .createdDate(cd)
                            .createdTime(ct)
                    .build()
            );
        }
        return ret;
    }
}
