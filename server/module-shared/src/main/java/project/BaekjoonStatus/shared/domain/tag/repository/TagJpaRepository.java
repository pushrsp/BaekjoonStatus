package project.BaekjoonStatus.shared.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String name);
    List<Tag> findByTagNameIn(List<String> names);
}
