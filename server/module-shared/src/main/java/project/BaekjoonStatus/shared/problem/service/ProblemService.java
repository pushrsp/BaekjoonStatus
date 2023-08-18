package project.BaekjoonStatus.shared.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;
import project.BaekjoonStatus.shared.problem.service.request.ProblemCreateSharedServiceRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    @Transactional
    public Problem save(ProblemCreateSharedServiceRequest request) {
        return problemRepository.save(request.toDomain());
    }

    @Transactional
    public Problem saveAndFlush(ProblemCreateSharedServiceRequest request) {
        return problemRepository.saveAndFlush(request.toDomain());
    }

    @Transactional
    public int saveAll(List<ProblemCreateSharedServiceRequest> requests) {
        if(ProblemCreateSharedServiceRequest.hasDuplicateId(requests)) {
            throw new IllegalArgumentException("중복된 id가 존재합니다.");
        }

        List<Problem> problems = requests.stream()
                .map(ProblemCreateSharedServiceRequest::toDomain)
                .collect(Collectors.toList());

        return problemRepository.saveAll(problems);
    }

    @Transactional(readOnly = true)
    public Optional<Problem> findById(String id) {
        return problemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Problem> findAllByIdsIn(List<String> ids) {
        return problemRepository.findAllByIdsIn(ids);
    }

    @Transactional(readOnly = true)
    public List<String> findAllByNotExistedIds(List<String> ids) {
        List<String> savedIds = findAllByIdsIn(ids).stream()
                .map(Problem::getId)
                .toList();

        return ids.stream()
                .filter(id -> !savedIds.contains(id))
                .collect(Collectors.toList());
    }
}
