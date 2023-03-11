package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;

import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistoryResp> findSolvedHistories(UUID userId, int offset, int limit);
    List<SolvedCountByDate> getSolvedCountGroupByDate(UUID userId, String year);
    List<SolvedCountByLevel> getSolvedCountGroupByLevel(UUID userId);
    List<SolvedCountByTag> getSolvedCountGroupByTag(UUID userId);
}
