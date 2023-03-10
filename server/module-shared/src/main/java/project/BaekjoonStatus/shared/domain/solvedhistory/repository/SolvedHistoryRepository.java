package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;

import java.util.List;
import java.util.UUID;

public interface SolvedHistoryRepository {
    public List<SolvedHistoryResp> findSolvedHistories(UUID userId, int offset, int limit);
}
