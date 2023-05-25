package project.BaekjoonStatus.shared.domain.user.repository;

import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    List<UserDto> findAllByGreaterThanUserId(Long userId, int limit);
}
