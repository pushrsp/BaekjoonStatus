package project.BaekjoonStatus.shared.domain.user.repository;

import project.BaekjoonStatus.shared.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    boolean existByUsername(String username);
    User save(User user);
    Optional<User> findById(String userId);
    Optional<User> findByUsername(String username);
}
