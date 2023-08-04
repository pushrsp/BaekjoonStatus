package project.BaekjoonStatus.shared.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.service.port.UserRepository;
import project.BaekjoonStatus.shared.user.service.request.UserCreateServiceDto;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(!isValidUsername(username)) {
            return Optional.empty();
        }

        return userRepository.findByUsername(username);
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.isBlank() && !hasSpecialCharacter(username);
    }

    private boolean hasSpecialCharacter(String username) {
        String specialCharactersPattern = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(specialCharactersPattern);
        Matcher matcher = pattern.matcher(username);

        return matcher.find();
    }

    @Transactional(readOnly = true)
    public List<User> findAllByGreaterThanUserId(Long userId, int limit) {
        return userRepository.findAllByGreaterThanUserId(userId, limit);
    }
}
