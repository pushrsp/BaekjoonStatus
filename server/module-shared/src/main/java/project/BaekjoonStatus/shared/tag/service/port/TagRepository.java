package project.BaekjoonStatus.shared.tag.service.port;

import project.BaekjoonStatus.shared.tag.infra.TagEntity;

import java.util.List;

public interface TagRepository {
    TagEntity save(TagEntity tag);
    List<TagEntity> saveAll(List<TagEntity> tags);
    List<TagEntity> findAllByProblemIdIn(List<Long> problemIds);
}
