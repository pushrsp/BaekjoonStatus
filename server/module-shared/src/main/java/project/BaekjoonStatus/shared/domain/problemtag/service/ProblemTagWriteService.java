package project.BaekjoonStatus.shared.domain.problemtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.domain.problemtag.repository.ProblemTagJpaRepository;
import project.BaekjoonStatus.shared.dto.command.ProblemCommand;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemTagWriteService {
    private final ProblemTagJpaRepository problemTagJpaRepository;
    public List<ProblemTag> bulkInsert(List<ProblemTag> problemTags) {
        return problemTagJpaRepository.saveAll(problemTags);
    }
}
