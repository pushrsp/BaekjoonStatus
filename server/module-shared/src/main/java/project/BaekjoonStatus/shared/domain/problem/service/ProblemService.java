package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
    private final ProblemJpaRepository problemJpaRepository;

    @Transactional
    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

    public List<Problem> findAll() {
        return problemJpaRepository.findAll();
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

    @Transactional
    public List<Long> findProblemIdsByNotInWithLock(List<Long> ids) {
        Set<Long> set = new HashSet<>(ids);

        List<Problem> problems = findAllByIdsWithLock(ids);
        for (Problem problem : problems)
            set.remove(problem.getId());

        return set.stream().toList();
    }

    @Transactional
    public List<Problem> findAllByIdsWithLock(List<Long> ids) {
        return problemJpaRepository.findAllByIdInWithLock(ids);
    }

    public Optional<Problem> findById(Long problemId) {
        return problemJpaRepository.findById(problemId);
    }

    @Transactional
    public List<Problem> bulkInsert(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems);
    }

    @Transactional
    public List<Problem> bulkInsertAndFlush(List<Problem> problems) {
        return problemJpaRepository.saveAllAndFlush(problems);
    }

    @Transactional
    public List<Problem> saveAll(List<SolvedAcProblemResp> infos) {
        return problemJpaRepository.saveAll(Problem.create(infos));
    }
}
