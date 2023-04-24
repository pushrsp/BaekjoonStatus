package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.List;
import java.util.UUID;

public interface SolvedHistoryJpaRepository  extends JpaRepository<SolvedHistory, UUID> {
    List<SolvedHistory> findAllByUserId(UUID userId);
}
