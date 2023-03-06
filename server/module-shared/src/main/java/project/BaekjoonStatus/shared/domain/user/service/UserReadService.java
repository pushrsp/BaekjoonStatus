package project.BaekjoonStatus.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserReadService {
    private final UserJpaRepository userJpaRepository;
}
