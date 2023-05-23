package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    @Transactional(readOnly = true)
    public List<Tag> findAllByProblemIdIn(List<Long> problemIds) {
        return tagRepository.findAllByProblemIdIn(problemIds);
    }
}
