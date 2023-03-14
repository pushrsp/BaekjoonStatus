package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;
import project.BaekjoonStatus.shared.domain.problemtag.service.ProblemTagWriteService;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

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

    public List<Problem> bulkInsertByCommandInfos(List<SolvedAcProblemResp> commandInfos) {
        return this.bulkInsert(Problem.create(commandInfos));
    }
}
