package project.BaekjoonStatus.shared.domain.problem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

import javax.persistence.LockModeType;
import java.util.List;

public interface ProblemJpaRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByIdIn(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Problem p where p.id in :ids")
    List<Problem> findAllByIdInWithLock(@Param("ids") List<Long> ids);
}
