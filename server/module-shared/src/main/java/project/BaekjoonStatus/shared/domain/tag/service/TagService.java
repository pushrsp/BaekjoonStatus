package project.BaekjoonStatus.shared.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.repository.TagRepository;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

//    public Tag createWithProblem(Problem problem, String tagName) {
//        return new Tag(problem, tagName);
//    }
//
//    public List<Tag> createWithInfosAndProblems(List<SolvedAcProblemResp> infos, List<Problem> problems) {
//        Map<Long, Problem> map = new HashMap<>();
//        for (Problem problem : problems)
//            map.put(problem.getId(), problem);
//
//        List<Tag> ret = new ArrayList<>();
//        for (SolvedAcProblemResp info : infos) {
//            Problem p = map.get(info.getProblemId());
//
//            for (SolvedAcProblemResp.Tag tag : info.getTags())
//                ret.add(createWithProblem(p, tag.getKey()));
//        }
//
//        return ret;
//    }
}
