package project.BaekjoonStatus.shared.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {
}
