package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;
import project.BaekjoonStatus.shared.util.DateProvider;

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
    public List<Problem> saveAll(List<Problem> problems) {
        return problemRepository.saveAll(problems);
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
        Set<Long> set = new HashSet<>(ids);
        List<Problem> existedProblems = findAllByIdsIn(ids);

        for (Problem existedProblem : existedProblems) {
            set.remove(existedProblem.getId());
        }

        return new ArrayList<>(set);
    }
}
