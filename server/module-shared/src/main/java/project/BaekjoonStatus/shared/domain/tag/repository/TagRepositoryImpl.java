package project.BaekjoonStatus.shared.domain.tag.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;

    @Override
    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    @Override
    public List<Tag> findAllByProblemIdIn(List<Long> problemIds) {
        return tagJpaRepository.findAllByProblemIdIn(problemIds);
    }
}
