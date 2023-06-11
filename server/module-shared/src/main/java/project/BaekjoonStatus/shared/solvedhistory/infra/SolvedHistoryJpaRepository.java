package project.BaekjoonStatus.shared.solvedhistory.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SolvedHistoryJpaRepository  extends JpaRepository<SolvedHistoryEntity, UUID> {
    @Query("select sh from SolvedHistoryEntity sh join fetch sh.problem where sh.user.id = :userId")
    List<SolvedHistoryEntity> findAllByUserId(@Param("userId") Long userId);
}
