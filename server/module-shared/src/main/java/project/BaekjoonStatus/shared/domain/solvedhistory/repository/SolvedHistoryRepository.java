package project.BaekjoonStatus.shared.domain.solvedhistory.repository;


import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;

import java.util.List;

import static project.BaekjoonStatus.shared.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistory> saveAll(List<SolvedHistory> solvedHistories);
    List<CountByDate> findSolvedCountGroupByDate(Long userId, String year);
    List<CountByLevel> findSolvedCountGroupByLevel(Long userId);
    List<CountByTag> findSolvedCountGroupByTag(Long userId);
    List<SolvedHistory> findAllByUserId(Long userId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(Long userId);
}
