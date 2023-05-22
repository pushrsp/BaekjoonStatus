package project.BaekjoonStatus.shared.domain.tag.repository;

import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);
    List<Tag> saveAll(List<Tag> tags);
    List<Tag> findAllByProblemIdIn(List<Long> problemIds);
}
