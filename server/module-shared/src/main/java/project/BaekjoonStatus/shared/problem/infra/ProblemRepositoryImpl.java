package project.BaekjoonStatus.shared.problem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.problem.service.port.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemRepositoryImpl implements ProblemRepository {
    private final ProblemJpaRepository problemJpaRepository;

    @Override
    @Transactional
    public List<ProblemEntity> saveAll(List<ProblemEntity> problems) {
        return problemJpaRepository.saveAll(problems);
    }

    @Override
    @Transactional
    public ProblemEntity save(ProblemEntity problem) {
        return problemJpaRepository.save(problem);
    }

    @Override
    public List<ProblemEntity> findAllByIdsIn(List<Long> ids) {
        return problemJpaRepository.findAllByIdIn(ids);
    }

    @Override
    public Optional<ProblemEntity> findById(Long id) {
        return problemJpaRepository.findById(id);
    }
}
