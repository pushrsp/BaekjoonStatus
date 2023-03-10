package project.BaekjoonStatus.shared.domain.user.repository;

import project.BaekjoonStatus.shared.domain.user.entity.User;

public interface UserRepository {
    User findByUsername(String username);
}
