package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;

@Service
@RequiredArgsConstructor
public class SolvedHistoryReadService {
    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;
}
