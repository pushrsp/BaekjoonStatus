package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagJpaRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagJpaRepository tagJpaRepository;

    public List<Tag> bulkInsert(List<Tag> tags) {
        return tagJpaRepository.saveAll(tags);
    }

    public List<Tag> saveAll( List<SolvedAcProblemResp> infos, List<Problem> problems) {
        Map<Long, Problem> map = new HashMap<>();
        for (Problem problem : problems)
            map.put(problem.getId(), problem);

        List<Tag> tags = new ArrayList<>();
        for (SolvedAcProblemResp info : infos) {
            Problem problem = map.get(info.getProblemId());

            for (SolvedAcProblemResp.Tag tag : info.getTags())
                tags.add(Tag.create(problem, tag.getKey()));
        }

        return bulkInsert(tags);
    }
}
