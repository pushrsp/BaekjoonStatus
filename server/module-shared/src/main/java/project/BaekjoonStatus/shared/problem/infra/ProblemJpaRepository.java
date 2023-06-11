package project.BaekjoonStatus.shared.problem.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemJpaRepository extends JpaRepository<ProblemEntity, Long> {
    List<ProblemEntity> findAllByIdIn(List<Long> ids);
}
