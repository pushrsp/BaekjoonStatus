package project.BaekjoonStatus.shared.dailyproblem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;
import project.BaekjoonStatus.shared.dailyproblem.service.port.DailyProblemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyProblemRepositoryImpl implements DailyProblemRepository {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;

    @Override
    @Transactional
    public List<DailyProblem> saveAll(List<DailyProblem> dailyProblems) {
        return dailyProblemJpaRepository.saveAll(dailyProblems.stream().map(DailyProblemEntity::from).collect(Collectors.toList()))
                .stream()
                .map(DailyProblemEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyProblem> findTodayProblems(LocalDate date) {
        return dailyProblemJpaRepository.findTop4ByCreatedDateGreaterThanEqualOrderByCreatedDateDesc(date)
                .stream()
                .map(DailyProblemEntity::to)
                .collect(Collectors.toList());
    }
}
