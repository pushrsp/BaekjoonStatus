package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryRepository;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedHistoryResp;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolvedHistoryReadService {
    private static final int PAGE_SIZE = 10;

    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;
    private final SolvedHistoryRepository solvedHistoryRepository;

    public List<SolvedHistoryResp> findSolvedHistories(String userId, Integer offset) {
        return solvedHistoryRepository.findSolvedHistories(UUID.fromString(userId), offset, PAGE_SIZE);
    }
}
