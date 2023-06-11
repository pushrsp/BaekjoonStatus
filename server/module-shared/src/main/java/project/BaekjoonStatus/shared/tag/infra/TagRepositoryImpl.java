package project.BaekjoonStatus.shared.tag.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.service.port.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;

    @Override
    @Transactional
    public Tag save(Tag tag) {
        return tagJpaRepository.save(TagEntity.from(tag)).to();
    }

    @Override
    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags.stream().map(TagEntity::from).collect(Collectors.toList()))
                .stream()
                .map(TagEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tag> findAllByProblemIdIn(List<Long> problemIds) {
        return tagJpaRepository.findAllByProblemIdIn(problemIds)
                .stream()
                .map(TagEntity::to)
                .collect(Collectors.toList());
    }
}
