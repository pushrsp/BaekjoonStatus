package project.BaekjoonStatus.shared.domain.dailyproblem.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyProblemRepositoryImpl implements DailyProblemRepository {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;

    @Override
    @Transactional
    public List<DailyProblem> saveAll(List<DailyProblem> dailyProblems) {
        return dailyProblemJpaRepository.saveAll(dailyProblems);
    }

    @Override
    public List<DailyProblem> findTodayProblems(LocalDate date) {
        return dailyProblemJpaRepository.findTop4ByCreatedDateGreaterThanEqualOrderByCreatedDateDesc(date);
    }
}
