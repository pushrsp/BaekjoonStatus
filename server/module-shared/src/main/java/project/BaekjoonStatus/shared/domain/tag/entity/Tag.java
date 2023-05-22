package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TAG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tag_id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    private Tag(String tagName) {
        validateTagName(tagName);

        this.tagName = tagName;
    }

    private Tag(Problem problem, String tagName) {
        validateProblem(problem);
        validateTagName(tagName);

        this.problem = problem;
        this.tagName = tagName;
    }

    private void validateTagName(String tagName) {
        Assert.notNull(tagName, "태그 이름을 입력해주세요.");
    }

    private void validateProblem(Problem problem) {
        Assert.notNull(problem.getId(), "문제를 입력해주세요.");
    }

    public static Tag of(String tagName) {
        return new Tag(tagName);
    }

    public static Tag ofWithProblem(Problem problem, String tagName) {
        return new Tag(problem, tagName);
    }

    public static List<Tag> ofWithInfosAndProblems(List<SolvedAcProblemResp> infos, List<Problem> problems) {
        Map<Long, Problem> map = new HashMap<>();
        for (Problem problem : problems) {
            map.put(problem.getId(), problem);
        }

        List<Tag> ret = new ArrayList<>();
        for (SolvedAcProblemResp info : infos) {
            Problem p = map.get(info.getProblemId());

            for (SolvedAcProblemResp.Tag tag : info.getTags()) {
                ret.add(Tag.ofWithProblem(p, tag.getKey()));
            }
        }

        return ret;
    }
}
