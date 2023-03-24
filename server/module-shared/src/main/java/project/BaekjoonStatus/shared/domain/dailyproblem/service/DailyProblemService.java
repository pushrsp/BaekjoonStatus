package project.BaekjoonStatus.shared.domain.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.repository.DailyProblemJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyProblemService {
    private final DailyProblemJpaRepository dailyProblemJpaRepository;

    public List<DailyProblem> bulkInsert(List<DailyProblem> dailyProblems) {
        return dailyProblemJpaRepository.saveAll(dailyProblems);
    }
}
