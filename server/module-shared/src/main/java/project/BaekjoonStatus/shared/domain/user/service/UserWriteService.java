package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.entity.User;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;
import project.BaekjoonStatus.shared.dto.command.UserCommand;

@Service
@RequiredArgsConstructor
public class UserWriteService {
    private final UserJpaRepository userJpaRepository;

    public Long save(UserCommand userCommand) {
        return userJpaRepository.save(User.create(userCommand)).getId();
    }
}
