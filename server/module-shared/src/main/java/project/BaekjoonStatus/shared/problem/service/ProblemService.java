package project.BaekjoonStatus.shared.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    @Transactional
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Transactional
    public Problem saveAndFlush(Problem problem) {
        return problemRepository.saveAndFlush(problem);
    }

    @Transactional
    public void saveAll(List<Problem> problems) {
        problemRepository.saveAll(problems);
    }

    @Transactional(readOnly = true)
    public Optional<Problem> findById(Long id) {
        return problemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Problem> findAllByIdsIn(List<Long> ids) {
        return problemRepository.findAllByIdsIn(ids);
    }

    @Transactional(readOnly = true)
    public List<Long> findAllByNotExistedIds(List<Long> ids) {
        List<Long> savedIds = findAllByIdsIn(ids).stream()
                .map(Problem::getId)
                .toList();

        return ids.stream()
                .filter(id -> !savedIds.contains(id))
                .collect(Collectors.toList());
    }
}
