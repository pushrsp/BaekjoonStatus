package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;

@Service
@RequiredArgsConstructor
public class TagReadService {
    private final TagJpaRepository tagJpaRepository;
}
