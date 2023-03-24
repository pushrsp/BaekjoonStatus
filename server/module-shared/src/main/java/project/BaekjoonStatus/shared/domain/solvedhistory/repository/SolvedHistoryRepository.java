package project.BaekjoonStatus.shared.domain.solvedhistory.repository;


import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<CountByDate> findSolvedCountGroupByDate(UUID userId, String year);
    List<CountByLevel> findSolvedCountGroupByLevel(UUID userId);
    List<CountByTag> findSolvedCountGroupByTag(UUID userId);
}
