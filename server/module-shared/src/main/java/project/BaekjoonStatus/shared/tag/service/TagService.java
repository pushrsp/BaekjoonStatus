package project.BaekjoonStatus.shared.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.infra.TagRepository;
import project.BaekjoonStatus.shared.tag.service.request.TagCreateSharedServiceRequest;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag save(TagCreateSharedServiceRequest request) {
        return tagRepository.save(request.toDomain());
    }

    @Transactional
    public int saveAll(List<TagCreateSharedServiceRequest> requests) {
        return tagRepository.saveAll(TagCreateSharedServiceRequest.toDomainList(requests));
    }

    @Transactional(readOnly = true)
    public List<Tag> findAllByProblemIdsIn(List<String> problemIds) {
        return tagRepository.findAllByProblemIdsIn(problemIds);
    }
}
