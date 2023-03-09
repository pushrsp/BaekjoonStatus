package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserWriteService {
    private final UserJpaRepository userJpaRepository;

    public User create(String username, String password, String baekjoonUsername) {
        return userJpaRepository.save(User.create(username, password, baekjoonUsername));
    }
}
