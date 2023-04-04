package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemJpaRepository problemJpaRepository;

    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

    public List<Problem> findAllByIds(List<Long> ids) {
        return problemJpaRepository.findAllByIdIn(ids);
    }

    public List<Long> findProblemIdsByNotIn(List<Long> ids) {
        Set<Long> set = new HashSet<>(ids);

        List<Problem> problems = findAllByIds(ids);
        for (Problem problem : problems)
            set.remove(problem.getId());

        return set.stream().toList();
    }

    public Optional<Problem> findById(Long problemId) {
        return problemJpaRepository.findById(problemId);
    }

    public List<Problem> bulkInsert(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems);
    }
    public List<Problem> bulkInsertAndFlush(List<Problem> problems) {
        return problemJpaRepository.saveAllAndFlush(problems);
    }

    public List<Problem> saveAll(List<SolvedAcProblemResp> infos) {
        return bulkInsert(Problem.create(infos));
    }
}
