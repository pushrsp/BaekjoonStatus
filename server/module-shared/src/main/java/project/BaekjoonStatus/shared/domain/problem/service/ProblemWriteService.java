package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.domain.problemtag.repository.ProblemTagJpaRepository;
import project.BaekjoonStatus.shared.domain.problemtag.service.ProblemTagWriteService;
import project.BaekjoonStatus.shared.dto.command.ProblemCommand;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemWriteService {
    private final ProblemJpaRepository problemJpaRepository;
    private final ProblemTagWriteService problemTagWriteService;

    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

    public List<Problem> bulkInsert(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems);
    }

    public void bulkInsertByProblemCommands(List<ProblemCommand> problemCommands) {
        Map<Long, ProblemCommand> ids = new HashMap<>();
        for (ProblemCommand problemCommand : problemCommands)
            ids.put(problemCommand.getProblemId(), problemCommand);

        List<Problem> findProblems = problemJpaRepository.findByIdIn(ids.keySet().stream().toList());
        for (Problem problem : findProblems)
            ids.remove(problem.getId());

        List<Problem> problems = bulkInsert(Problem.create(ids.values().stream().toList()));

        List<ProblemTag> problemTags = new ArrayList<>();
        for (Problem problem : problems) {
            ProblemCommand problemCommand = ids.get(problem.getId());
            problemTags.addAll(ProblemTag.create(problem, problemCommand.getTags()));
        }

        problemTagWriteService.bulkInsert(problemTags);
    }
}
