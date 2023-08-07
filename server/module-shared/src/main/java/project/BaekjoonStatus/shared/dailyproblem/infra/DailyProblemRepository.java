package project.BaekjoonStatus.shared.dailyproblem.infra;

import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemRepository {
    void saveAll(List<DailyProblem> dailyProblems);
    List<DailyProblem> findTodayProblems(LocalDate date);
}
