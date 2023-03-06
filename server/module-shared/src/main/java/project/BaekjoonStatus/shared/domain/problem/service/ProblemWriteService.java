package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;
import project.BaekjoonStatus.shared.dto.command.ProblemCommand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemWriteService {
    private final ProblemJpaRepository problemJpaRepository;

    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

    public int bulkInsert(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems).size();
    }
}
