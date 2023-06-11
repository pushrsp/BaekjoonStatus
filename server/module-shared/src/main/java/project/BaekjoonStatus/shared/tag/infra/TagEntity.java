package project.BaekjoonStatus.shared.tag.infra;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.common.service.solvedac.response.SolvedAcProblemResponse;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TAG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tag_id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private ProblemEntity problem;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    private TagEntity(ProblemEntity problem, String tagName) {
        validateProblem(problem);
        validateTagName(tagName);

        this.problem = problem;
        this.tagName = tagName;
    }

    private void validateTagName(String tagName) {
        Assert.notNull(tagName, "태그 이름을 입력해주세요.");
        Assert.hasText(tagName, "태그 이름을 입력해주세요.");
    }

    private void validateProblem(ProblemEntity problem) {
        Assert.notNull(problem, "문제를 입력해주세요.");
    }

    public static TagEntity ofWithProblem(ProblemEntity problem, String tagName) {
        return new TagEntity(problem, tagName);
    }

    public static List<TagEntity> ofWithInfosAndProblems(List<SolvedAcProblemResponse> infos, List<ProblemEntity> problems) {
        List<TagEntity> ret = new ArrayList<>();
        if(Objects.isNull(infos) || Objects.isNull(problems)) {
            return ret;
        }

        Map<Long, ProblemEntity> map = new HashMap<>();
        for (ProblemEntity problem : problems) {
            map.put(problem.getId(), problem);
        }

        for (SolvedAcProblemResponse info : infos) {
            ProblemEntity p = map.get(info.getProblemId());

            for (SolvedAcProblemResponse.Tag tag : info.getTags()) {
                ret.add(TagEntity.ofWithProblem(p, tag.getKey()));
            }
        }

        return ret;
    }
}
