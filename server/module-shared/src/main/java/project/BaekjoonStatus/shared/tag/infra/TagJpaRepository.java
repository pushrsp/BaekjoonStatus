package project.BaekjoonStatus.shared.tag.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByProblemIdIn(List<Long> problemIds);
}
