package project.BaekjoonStatus.shared.tag.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.service.port.TagRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;

    @Override
    public TagEntity save(TagEntity tag) {
        return tagJpaRepository.save(tag);
    }

    @Override
    @Transactional
    public List<TagEntity> saveAll(List<TagEntity> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    @Override
    public List<TagEntity> findAllByProblemIdIn(List<Long> problemIds) {
        return tagJpaRepository.findAllByProblemIdIn(problemIds);
    }
}
