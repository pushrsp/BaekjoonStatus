package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagJpaRepository tagJpaRepository;

    public List<Tag> bulkInsert(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    public List<Tag> saveAllByNotIn(Set<String> names) {
        List<Tag> findTags = tagJpaRepository.findByNameIn(names.stream().toList());
        for (Tag tag : findTags)
            names.remove(tag.getName());

        List<Tag> saveTags = bulkInsert(Tag.createByNames(names));
        findTags.addAll(saveTags);

        return findTags;
    }
}
