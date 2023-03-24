package project.BaekjoonStatus.shared.domain.dailyproblem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.dailyproblem.entity.DailyProblem;

import java.util.UUID;

public interface DailyProblemJpaRepository extends JpaRepository<DailyProblem, UUID> {
}
