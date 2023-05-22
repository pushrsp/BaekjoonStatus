package project.BaekjoonStatus.shared.domain.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.dailyproblem.repository.DailyProblemRepository;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyProblemService {
    private final DailyProblemRepository dailyProblemRepository;

    @Transactional(readOnly = true)
    public List<DailyProblem> findTodayProblems(LocalDate date) {
        return dailyProblemRepository.findTodayProblems(date);
    }

    public List<DailyProblem> saveAll(List<DailyProblem> dailyProblems) {
        return dailyProblemRepository.saveAll(dailyProblems);
    }
}
