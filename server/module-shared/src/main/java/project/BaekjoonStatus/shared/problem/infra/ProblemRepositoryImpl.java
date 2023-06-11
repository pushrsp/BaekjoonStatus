package project.BaekjoonStatus.shared.problem.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.service.port.ProblemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemRepositoryImpl implements ProblemRepository {
    private final ProblemJpaRepository problemJpaRepository;

    @Override
    @Transactional
    public List<Problem> saveAll(List<Problem> problems) {
        return problemJpaRepository.saveAll(problems.stream().map(ProblemEntity::from).collect(Collectors.toList()))
                .stream()
                .map(ProblemEntity::to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Problem save(Problem problem) {
        return problemJpaRepository.save(ProblemEntity.from(problem)).to();
    }

    @Override
    public List<Problem> findAllByIdsIn(List<Long> ids) {
        return problemJpaRepository.findAllByIdIn(ids)
                .stream()
                .map(ProblemEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Problem> findById(Long id) {
        return problemJpaRepository.findById(id).map(ProblemEntity::to);
    }
}
