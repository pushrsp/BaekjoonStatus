package project.BaekjoonStatus.shared.domain.problemtag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PROBLEM_TAG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemTag {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "problem_tag_id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @Type(type = "uuid-char")
    private Tag tag;

    public void setProblem(Problem problem) {
        this.problem = problem;
        problem.getProblemTags().add(this);
    }

    private void setTag(Tag tag) {
        this.tag = tag;
    }

    private ProblemTag(Problem problem, Tag tag) {
        setProblem(problem);
        setTag(tag);
    }

    /* 생성 메서드 */
    public static ProblemTag create(Problem problem, Tag tag) {
        return new ProblemTag(problem, tag);
    }

    public static List<ProblemTag> create(Problem problem, List<Tag> tags) {
        List<ProblemTag> problemTags = new ArrayList<>();
        for (Tag tag : tags)
            problemTags.add(ProblemTag.create(problem, tag));

        return problemTags;
    }
}
