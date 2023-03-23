package project.BaekjoonStatus.shared.domain.solvedhistory.repository;

import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistoryByUserId> findSolvedHistories(UUID userId, int offset, int limit);
    List<CountByDate> getSolvedCountGroupByDate(UUID userId, String year);
    List<CountByLevel> getSolvedCountGroupByLevel(UUID userId);
    List<CountByTag> getSolvedCountGroupByTag(UUID userId);
}
