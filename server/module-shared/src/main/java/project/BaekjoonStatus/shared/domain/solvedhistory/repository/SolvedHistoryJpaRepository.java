package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.List;
import java.util.UUID;

public interface SolvedHistoryJpaRepository  extends JpaRepository<SolvedHistory, UUID> {
    @Query("select sh from SolvedHistory sh join fetch sh.problem where sh.user.id = :userId")
    List<SolvedHistory> findAllByUserId(@Param("userId") Long userId);
}
