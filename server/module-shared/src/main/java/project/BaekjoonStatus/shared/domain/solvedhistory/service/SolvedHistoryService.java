package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByDate;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByTag;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    private final SolvedHistoryRepository solvedHistoryRepository;

    @Transactional
    public List<SolvedHistory> saveAll(List<SolvedHistory> solvedHistories) {
        return solvedHistoryRepository.saveAll(solvedHistories);
    }

    @Transactional(readOnly = true)
    public List<SolvedHistory> findByUserId(Long userId) {
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
    public List<SolvedHistory> findAllByUserId(Long userId, int offset, int pageSize) {
        return solvedHistoryRepository.findAllByUserId(userId, offset, pageSize);
    }
}
