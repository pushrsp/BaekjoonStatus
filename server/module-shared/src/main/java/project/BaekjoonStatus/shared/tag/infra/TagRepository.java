package project.BaekjoonStatus.shared.tag.infra;

import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

public interface TagRepository {
    Tag save(Tag tag);
    int saveAll(List<Tag> tags);
    List<Tag> findAllByProblemIdsIn(List<String> problemIds);
    void  deleteAllInBatch();
}
