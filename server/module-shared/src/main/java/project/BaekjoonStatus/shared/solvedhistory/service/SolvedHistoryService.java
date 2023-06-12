package project.BaekjoonStatus.shared.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.solvedhistory.domain.SolvedHistory;
import project.BaekjoonStatus.shared.solvedhistory.infra.SolvedHistoryEntity;
import project.BaekjoonStatus.shared.solvedhistory.service.port.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.common.domain.dto.SolvedHistoryDto.CountByTag;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    private final SolvedHistoryRepository solvedHistoryRepository;

    @Transactional
    public void saveAll(List<SolvedHistory> solvedHistories) {
        solvedHistoryRepository.saveAll(solvedHistories);
    }

    @Transactional(readOnly = true)
    public List<SolvedHistory> findAllByUserId(Long userId) {
        return solvedHistoryRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<CountByDate> findSolvedCountGroupByDate(Long userId, String year) {
        return solvedHistoryRepository.findSolvedCountGroupByDate(userId, year);
    }

    @Transactional(readOnly = true)
    public List<CountByLevel> findSolvedCountGroupByLevel(Long userId) {
        return solvedHistoryRepository.findSolvedCountGroupByLevel(userId);
    }

    @Transactional(readOnly = true)
    public List<CountByTag> findSolvedCountGroupByTag(Long userId) {
        return solvedHistoryRepository.findSolvedCountGroupByTag(userId);
    }

    @Transactional(readOnly = true)
    public List<SolvedHistoryEntity> findAllByUserId(Long userId, int offset, int pageSize) {
        return solvedHistoryRepository.findAllByUserId(userId, offset, pageSize);
    }
}
