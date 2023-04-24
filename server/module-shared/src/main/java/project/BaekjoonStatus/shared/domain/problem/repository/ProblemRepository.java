package project.BaekjoonStatus.shared.domain.problem.repository;

import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    List<Long> findNotSavedProblemIds(List<Long> ids);
    List<Problem> saveAll(List<Problem> problems);
    Problem save(Problem problem);
    List<Problem> findAllByIdsWithLock(List<Long> ids);
    List<Problem> findAllByIds(List<Long> ids);
    Optional<Problem> findById(Long id);
}
