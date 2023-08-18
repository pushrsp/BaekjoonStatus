package project.BaekjoonStatus.shared.problem.infra;

import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    int saveAll(List<Problem> problems);
    Problem save(Problem problem);
    Problem saveAndFlush(Problem problem);
    List<Problem> findAllByIdsIn(List<String> ids);
    Optional<Problem> findById(String id);
    void deleteAllInBatch();
}
