package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagWriteService {
    private final TagJpaRepository tagJpaRepository;

    public List<Tag> bulkInsert(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    public List<Tag> bulkInsertByCommandTags(List<SolvedAcProblemResp.Tag> commandTags) {
        Set<String> tagNames = new HashSet<>();
        for (SolvedAcProblemResp.Tag commandTag : commandTags)
            tagNames.add(commandTag.getKey());

        List<Tag> findTags = tagJpaRepository.findByNameIn(tagNames.stream().toList());
        for (Tag tag : findTags)
            tagNames.remove(tag.getName());

        findTags.addAll(bulkInsert(Tag.createByNames(tagNames.stream().toList())));

        return findTags;
    }
}
