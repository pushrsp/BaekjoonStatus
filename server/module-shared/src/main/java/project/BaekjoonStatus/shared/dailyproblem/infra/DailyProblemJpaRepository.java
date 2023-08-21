package project.BaekjoonStatus.shared.dailyproblem.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DailyProblemJpaRepository extends JpaRepository<DailyProblemEntity, UUID> {
    @Query("select dp from DailyProblemEntity dp join fetch dp.problem where dp.createdDate = :date")
    List<DailyProblemEntity> findAllByCreatedDate(@Param(value = "date") LocalDate date);
}
