package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;
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
    public List<SolvedHistory> findByUserId(String userId) {
        return solvedHistoryRepository.findAllByUserId(userId);
    }
}
