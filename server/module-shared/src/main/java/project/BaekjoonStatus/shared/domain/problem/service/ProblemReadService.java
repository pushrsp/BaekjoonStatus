package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemReadService {
    private final ProblemJpaRepository problemJpaRepository;

    public List<Problem> findByIds(List<Long> ids) {
        return problemJpaRepository.findByIdIn(ids);
    }
}
