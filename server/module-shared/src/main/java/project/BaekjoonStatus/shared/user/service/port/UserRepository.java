package project.BaekjoonStatus.shared.user.service.port;

import project.BaekjoonStatus.shared.common.domain.dto.UserDto;
import project.BaekjoonStatus.shared.user.infra.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long userId);
    Optional<UserEntity> findByUsername(String username);
    List<UserDto> findAllByGreaterThanUserId(Long userId, int limit);
}
