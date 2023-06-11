package project.BaekjoonStatus.shared.dailyproblem.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DailyProblemJpaRepository extends JpaRepository<DailyProblemEntity, UUID> {
    List<DailyProblemEntity> findTop4ByCreatedDateGreaterThanEqualOrderByCreatedDateDesc(LocalDate date);
}
