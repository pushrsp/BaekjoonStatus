package project.BaekjoonStatus.shared.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.repository.ProblemJpaRepository;

@Service
@RequiredArgsConstructor
public class ProblemReadService {
    private final ProblemJpaRepository problemJpaRepository;
}
