package project.BaekjoonStatus.shared.problem.service.port;

import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {
    List<ProblemEntity> saveAll(List<ProblemEntity> problems);
    ProblemEntity save(ProblemEntity problem);
    List<ProblemEntity> findAllByIdsIn(List<Long> ids);
    Optional<ProblemEntity> findById(Long id);
}
