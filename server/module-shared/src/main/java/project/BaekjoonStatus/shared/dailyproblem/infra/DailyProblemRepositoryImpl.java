package project.BaekjoonStatus.shared.dailyproblem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.dailyproblem.service.port.DailyProblemRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyProblemRepositoryImpl implements DailyProblemRepository {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;

    @Override
    @Transactional
    public List<DailyProblemEntity> saveAll(List<DailyProblemEntity> dailyProblems) {
        return dailyProblemJpaRepository.saveAll(dailyProblems);
    }

    @Override
    public List<DailyProblemEntity> findTodayProblems(LocalDate date) {
        return dailyProblemJpaRepository.findTop4ByCreatedDateGreaterThanEqualOrderByCreatedDateDesc(date);
    }
}
