package project.BaekjoonStatus.shared.dailyproblem.infra;

import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;

import java.time.LocalDate;
import java.util.List;

public interface DailyProblemRepository {
    int saveAll(List<DailyProblem> dailyProblems);
    List<DailyProblem> findAllByCreatedDate(LocalDate date);
    void deleteAllInBatch();
}
