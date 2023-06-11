package project.BaekjoonStatus.shared.problem.service.port;

import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    List<Problem> saveAll(List<Problem> problems);
    Problem save(Problem problem);
    List<Problem> findAllByIdsIn(List<Long> ids);
    Optional<Problem> findById(Long id);
}
