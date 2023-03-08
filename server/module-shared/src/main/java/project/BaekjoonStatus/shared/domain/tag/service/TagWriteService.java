package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;
import project.BaekjoonStatus.shared.dto.command.TagCommand;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagWriteService {
    private final TagJpaRepository tagJpaRepository;

    public List<Tag> bulkInsert(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    public Map<String, UUID> bulkInsertByTagCommands(List<TagCommand> tagCommands) {
        Set<String> tagNames = new HashSet<>();
        for (TagCommand tagCommand : tagCommands)
            tagNames.add(tagCommand.getName());

        List<Tag> findTags = tagJpaRepository.findByNameIn(tagNames.stream().toList());
        for (Tag tag : findTags)
            tagNames.remove(tag.getName());

        findTags.addAll(bulkInsert(Tag.createByTagNames(tagNames.stream().toList())));

        return TagCommand.toCommandFromTagsIntoMap(findTags);
    }
}
