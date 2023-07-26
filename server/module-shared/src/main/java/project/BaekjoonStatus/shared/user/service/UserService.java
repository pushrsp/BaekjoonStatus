package project.BaekjoonStatus.shared.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.port.UserRepository;
import project.BaekjoonStatus.shared.user.service.request.UserCreateServiceDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(UserCreateServiceDto request) {
        return userRepository.save(request.toDomain());
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        if(username.isEmpty()) {
            return Optional.empty();
        }

        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> findAllByGreaterThanUserId(Long userId, int limit) {
        return userRepository.findAllByGreaterThanUserId(userId, limit);
    }
}
