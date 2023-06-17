package project.BaekjoonStatus.shared.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.port.TagRepository;

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
    public void saveAll(List<Tag> tags) {
        tagRepository.saveAll(tags);
    }

    @Transactional(readOnly = true)
    public List<Tag> findByProblemIdsIn(List<Long> problemIds) {
        return tagRepository.findByProblemIdsIn(problemIds);
    }
}
