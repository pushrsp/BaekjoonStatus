package project.BaekjoonStatus.shared.tag.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    @Query("select t from TagEntity t join fetch t.problem where t.problem.id in :problemIds")
    List<TagEntity> findAllByProblemIdIn(@Param(value = "problemIds") List<Long> problemIds);
}
