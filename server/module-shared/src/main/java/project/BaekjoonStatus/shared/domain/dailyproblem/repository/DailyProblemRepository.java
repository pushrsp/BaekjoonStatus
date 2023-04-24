package project.BaekjoonStatus.shared.domain.dailyproblem.repository;

import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemRepository {
    List<DailyProblem> saveAll(List<DailyProblem> dailyProblems);
    List<DailyProblem> findTodayProblems(LocalDate date);
}
