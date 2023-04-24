package project.BaekjoonStatus.shared.domain.solvedhistory.repository;


import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.List;
import java.util.UUID;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistory> saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedCountGroupByDate(UUID userId, String year);
    List<CountByLevel> findSolvedCountGroupByLevel(UUID userId);
    List<CountByTag> findSolvedCountGroupByTag(UUID userId);
    List<SolvedHistory> findAllByUserId(UUID userId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(String userId);
}
