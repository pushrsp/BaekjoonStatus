package project.BaekjoonStatus.shared.domain.problemtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.domain.problemtag.repository.ProblemTagJpaRepository;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProblemTagService {
    private final ProblemTagJpaRepository problemTagJpaRepository;

    public List<ProblemTag> bulkInsert(List<ProblemTag> problemTags) {
        return problemTagJpaRepository.saveAll(problemTags);
    }

    public void saveAllByProblemInfos(List<SolvedAcProblemResp> infos, List<Problem> problems, List<Tag> tags) {
        Map<String, Tag> tagToMap = new HashMap<>();
        for (Tag tag : tags)
            tagToMap.put(tag.getName(), tag);

        Map<Long, Problem> problemToMap = new HashMap<>();
        for (Problem problem : problems)
            problemToMap.put(problem.getId(), problem);

        List<ProblemTag> problemTags = new ArrayList<>();
        for (SolvedAcProblemResp info : infos) {
            List<Tag> tagList = new ArrayList<>();
            for (SolvedAcProblemResp.Tag tag : info.getTags())
                tagList.add(tagToMap.get(tag.getKey()));

            problemTags.addAll(ProblemTag.create(problemToMap.get(info.getProblemId()), tagList));
        }

        bulkInsert(problemTags);
    }
}
