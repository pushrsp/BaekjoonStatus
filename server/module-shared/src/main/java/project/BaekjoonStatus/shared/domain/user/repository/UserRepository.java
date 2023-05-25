package project.BaekjoonStatus.shared.domain.user.repository;

import project.BaekjoonStatus.shared.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
}
