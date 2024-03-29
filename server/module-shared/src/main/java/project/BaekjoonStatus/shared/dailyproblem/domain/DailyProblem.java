package project.BaekjoonStatus.shared.dailyproblem.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class DailyProblem implements Serializable {
    private final String id;
    private final Problem problem;
    private final LocalDate createdDate;

    @Builder
    private DailyProblem(String id, Problem problem, LocalDate createdDate) {
        this.id = id;
        this.problem = problem;
        this.createdDate = createdDate;
    }

    public static DailyProblem of(Problem problem, LocalDate createdDate) {
        return DailyProblem.builder()
                .problem(problem)
                .createdDate(createdDate)
                .build();
    }
}
