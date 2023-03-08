package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserReadService {
    private final UserJpaRepository userJpaRepository;

    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    public boolean existByUsername(String username) {
        return userJpaRepository.existsUserByUsername(username);
    }
}
