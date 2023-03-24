package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.UUID;

public interface SolvedHistoryJpaRepository  extends JpaRepository<SolvedHistory, UUID> {
    Slice<SolvedHistory> findByUserId(UUID userId, Pageable pageable);
}
