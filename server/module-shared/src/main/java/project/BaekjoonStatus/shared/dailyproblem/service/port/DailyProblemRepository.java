package project.BaekjoonStatus.shared.dailyproblem.service.port;

import project.BaekjoonStatus.shared.dailyproblem.infra.DailyProblemEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemRepository {
    List<DailyProblemEntity> saveAll(List<DailyProblemEntity> dailyProblems);
    List<DailyProblemEntity> findTodayProblems(LocalDate date);
}
