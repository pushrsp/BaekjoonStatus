package project.BaekjoonStatus.shared.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.dailyproblem.infra.DailyProblemEntity;
import project.BaekjoonStatus.shared.dailyproblem.service.port.DailyProblemRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyProblemService {
    private final DailyProblemRepository dailyProblemRepository;

    @Transactional(readOnly = true)
    public List<DailyProblemEntity> findTodayProblems(LocalDate date) {
        return dailyProblemRepository.findTodayProblems(date);
    }

    public List<DailyProblemEntity> saveAll(List<DailyProblemEntity> dailyProblems) {
        return dailyProblemRepository.saveAll(dailyProblems);
    }
}
