package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagWriteService {
    private final TagJpaRepository tagJpaRepository;

    public int bulkInsert(List<Tag> tags) {
       return tagJpaRepository.saveAll(tags).size();
    }
}
