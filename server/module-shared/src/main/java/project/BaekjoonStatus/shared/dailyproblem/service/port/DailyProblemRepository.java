package project.BaekjoonStatus.shared.dailyproblem.service.port;

import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemRepository {
    List<DailyProblem> saveAll(List<DailyProblem> dailyProblems);
    List<DailyProblem> findTodayProblems(LocalDate date);
}
