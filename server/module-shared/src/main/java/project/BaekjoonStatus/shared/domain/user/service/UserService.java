package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    public Optional<User> findById(String userId) {
        return userJpaRepository.findById(UUID.fromString(userId));
    }

    public boolean exist(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Transactional
    public User save(String username, String baekjoonUsername, String password) {
        return userJpaRepository.save(User.create(username, baekjoonUsername, password));
    }
}
