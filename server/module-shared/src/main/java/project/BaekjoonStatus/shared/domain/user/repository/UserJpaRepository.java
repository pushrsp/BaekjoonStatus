package project.BaekjoonStatus.shared.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.BaekjoonStatus.shared.domain.user.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
