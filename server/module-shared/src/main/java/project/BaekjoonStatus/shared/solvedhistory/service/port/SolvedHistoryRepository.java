package project.BaekjoonStatus.shared.solvedhistory.service.port;


import project.BaekjoonStatus.shared.solvedhistory.domain.*;

import java.util.List;

public interface SolvedHistoryRepository {
    void saveAll(List<SolvedHistory> solvedHistories);
    List<GroupByDate> findSolvedCountGroupByDate(Long userId, String year);
    List<GroupByTier> findSolvedCountGroupByLevel(Long userId);
    List<GroupByTag> findSolvedCountGroupByTag(Long userId);
    List<SolvedHistoryByUserId> findAllByUserId(Long userId, int offset, int limit);
    List<SolvedHistory> findAllByUserId(Long userId);
}
