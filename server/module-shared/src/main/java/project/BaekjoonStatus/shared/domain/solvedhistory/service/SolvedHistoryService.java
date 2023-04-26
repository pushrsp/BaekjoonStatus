package project.BaekjoonStatus.shared.domain.solvedhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.util.DateProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedHistoryService {
    public SolvedHistory create(User user, Problem problem, boolean isBefore) {
        return new SolvedHistory(user, problem, isBefore, DateProvider.getDate(), DateProvider.getDateTime());
    }

    public SolvedHistory create(User user, Problem problem, boolean isBefore, LocalDate createDate, LocalDateTime createdTime) {
        return new SolvedHistory(user, problem, isBefore, createDate, createdTime);
    }

    public List<SolvedHistory> createWithProblems(User user, List<Problem> problems, boolean isBefore) {
        List<SolvedHistory> ret = new ArrayList<>();
        for (Problem problem : problems)
            ret.add(create(user, problem, isBefore));

        return ret;
    }
}
