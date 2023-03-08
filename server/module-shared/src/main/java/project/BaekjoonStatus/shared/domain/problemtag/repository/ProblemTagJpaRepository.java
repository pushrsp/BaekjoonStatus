package project.BaekjoonStatus.shared.domain.problemtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;

public interface ProblemTagJpaRepository extends JpaRepository<ProblemTag, Long> {
}
