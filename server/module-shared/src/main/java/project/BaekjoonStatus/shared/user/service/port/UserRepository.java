package project.BaekjoonStatus.shared.user.service.port;

import project.BaekjoonStatus.shared.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    List<User> findAllByGreaterThanUserId(Long userId, int limit);
    void deleteAllInBatch();
}
