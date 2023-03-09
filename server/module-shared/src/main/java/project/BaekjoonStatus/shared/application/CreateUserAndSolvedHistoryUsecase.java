package project.BaekjoonStatus.shared.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemReadService;
import project.BaekjoonStatus.shared.domain.solvedhistory.service.SolvedHistoryWriteService;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.service.UserWriteService;
import project.BaekjoonStatus.shared.dto.command.CreateUserAndSolvedHistoryCommand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserAndSolvedHistoryUsecase {
    private final UserWriteService userWriteService;
    private final ProblemReadService problemReadService;
    private final SolvedHistoryWriteService solvedHistoryWriteService;

    public void execute(CreateUserAndSolvedHistoryCommand command) {
        List<Problem> problems = problemReadService.findByIds(command.getSolvedHistories());
        User user = userWriteService.create(command.getUsername(), command.getPassword(), command.getBaekjoonUsername());

        solvedHistoryWriteService.bulkInsert(user, problems, command.getIsBefore());
    }
}
