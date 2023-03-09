package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.solvedhistory.repository.SolvedHistoryJpaRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedHistoryWriteService {
    private final SolvedHistoryJpaRepository solvedHistoryJpaRepository;

    public List<SolvedHistory> bulkInsert(List<SolvedHistory> solvedHistories) {
        return solvedHistoryJpaRepository.saveAll(solvedHistories);
    }

    public List<SolvedHistory> bulkInsert(User user, List<Problem> problems, boolean isBefore) {
        return bulkInsert(SolvedHistory.create(user, problems, isBefore));
    }
}
