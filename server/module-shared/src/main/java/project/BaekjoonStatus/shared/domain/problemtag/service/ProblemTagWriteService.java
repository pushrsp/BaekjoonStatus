package project.BaekjoonStatus.shared.domain.problemtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problemtag.entity.ProblemTag;
import project.BaekjoonStatus.shared.domain.problemtag.repository.ProblemTagJpaRepository;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProblemTagWriteService {
    private final ProblemTagJpaRepository problemTagJpaRepository;

    public List<ProblemTag> bulkInsert(List<ProblemTag> problemTags) {
        return problemTagJpaRepository.saveAll(problemTags);
    }

    public void bulkInsertByProblemInfos(List<SolvedAcProblemResp> problemInfos, List<Problem> problems, List<Tag> tags) {
        Map<String, Tag> tagToMap = new HashMap<>();
        for (Tag tag : tags)
            tagToMap.put(tag.getName(), tag);

        Map<Long, Problem> problemToMap = new HashMap<>();
        for (Problem problem : problems)
            problemToMap.put(problem.getId(), problem);

        List<ProblemTag> problemTags = new ArrayList<>();
        for (SolvedAcProblemResp info : problemInfos) {
            List<Tag> tagList = new ArrayList<>();
            for (SolvedAcProblemResp.Tag tag : info.getTags())
                tagList.add(tagToMap.get(tag.getKey()));

            problemTags.addAll(ProblemTag.create(problemToMap.get(info.getProblemId()), tagList));
        }

        bulkInsert(problemTags);
    }
}
