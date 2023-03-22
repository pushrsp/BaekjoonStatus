package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public boolean exist(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    public User save(String username, String baekjoonUsername, String password) {
        return userJpaRepository.save(User.create(username, baekjoonUsername, password));
    }
}
