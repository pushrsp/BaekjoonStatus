package project.BaekjoonStatus.shared.domain.solvedhistory.repository;


import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.List;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistory> saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedCountGroupByDate(String userId, String year);
    List<CountByLevel> findSolvedCountGroupByLevel(String userId);
    List<CountByTag> findSolvedCountGroupByTag(String userId);
    List<SolvedHistory> findAllByUserId(String userId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(String userId);
}
