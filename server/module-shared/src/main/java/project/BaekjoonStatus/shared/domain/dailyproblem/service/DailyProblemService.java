package project.BaekjoonStatus.shared.domain.dailyproblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.util.DateProvider;

@Service
@RequiredArgsConstructor
public class DailyProblemService {
    public DailyProblem create(Problem problem) {
        return new DailyProblem(problem, DateProvider.getDate());
    }
}
