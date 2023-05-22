package project.BaekjoonStatus.shared.domain.problem.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemRepositoryImpl implements ProblemRepository {
    private final ProblemJpaRepository problemJpaRepository;

    @Override
    @Transactional
    public List<Problem> saveAll(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems);
    }

    @Override
    @Transactional
    public Problem save(Problem problem) {
        return problemJpaRepository.save(problem);
    }

    @Override
    public List<Problem> findAllByIdsIn(List<Long> ids) {
        return problemJpaRepository.findAllByIdIn(ids);
    }

    @Override
    public Optional<Problem> findById(Long id) {
        return problemJpaRepository.findById(id);
    }
}
