package project.BaekjoonStatus.shared.tag.infra;

import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);
    void saveAll(List<Tag> tags);
    List<Tag> findByProblemIdsIn(List<Long> problemIds);
}
