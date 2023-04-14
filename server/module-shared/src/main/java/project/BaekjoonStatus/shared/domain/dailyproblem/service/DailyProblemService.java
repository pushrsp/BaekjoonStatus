package project.BaekjoonStatus.shared.domain.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.repository.DailyProblemJpaRepository;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyProblemService {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;

    public List<DailyProblem> findDailyProblems() {
        LocalDate now = DateProvider.getDate().minusDays(1);
        return dailyProblemJpaRepository.findTop4ByCreatedDateGreaterThanEqualOrderByCreatedDateDesc(LocalDate.of(now.getYear(), now.getMonth(),now.getDayOfMonth()));
    }

    @Transactional
    public List<DailyProblem> bulkInsert(List<DailyProblem> dailyProblems) {
        return dailyProblemJpaRepository.saveAll(dailyProblems);
    }
}
