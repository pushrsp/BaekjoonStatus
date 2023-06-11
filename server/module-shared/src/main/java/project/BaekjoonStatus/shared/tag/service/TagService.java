package project.BaekjoonStatus.shared.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.infra.TagEntity;
import project.BaekjoonStatus.shared.tag.service.port.TagRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public TagEntity save(TagEntity tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public List<TagEntity> saveAll(List<TagEntity> tags) {
        return tagRepository.saveAll(tags);
    }

    @Transactional(readOnly = true)
    public List<TagEntity> findAllByProblemIdIn(List<Long> problemIds) {
        return tagRepository.findAllByProblemIdIn(problemIds);
    }
}
