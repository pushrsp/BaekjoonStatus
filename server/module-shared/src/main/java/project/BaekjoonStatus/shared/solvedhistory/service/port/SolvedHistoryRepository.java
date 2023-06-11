package project.BaekjoonStatus.shared.solvedhistory.service.port;


import project.BaekjoonStatus.shared.solvedhistory.infra.SolvedHistoryEntity;

import java.util.List;

import static project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.*;

public interface SolvedHistoryRepository {
    List<SolvedHistoryEntity> saveAll(List<SolvedHistoryEntity> solvedHistories);
    List<CountByDate> findSolvedCountGroupByDate(Long userId, String year);
    List<CountByLevel> findSolvedCountGroupByLevel(Long userId);
    List<CountByTag> findSolvedCountGroupByTag(Long userId);
    List<SolvedHistoryEntity> findAllByUserId(Long userId, int offset, int limit);
    List<SolvedHistoryEntity> findAllByUserId(Long userId);
}
