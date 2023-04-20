package project.BaekjoonStatus.shared.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByProblemIdIn(List<Long> problemIds);
}
