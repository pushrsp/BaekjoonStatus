package project.BaekjoonStatus.shared.domain.problemtag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.command.TagCommand;

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
    @GeneratedValue()
    @Column(name = "problem_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @Type(type = "uuid-char")
    private Tag tag;

    public void setProblem(Problem problem) {
        this.problem = problem;
        this.problem.getProblemTags().add(this);
    }

    private void setTag(Tag tag) {
        this.tag = tag;
    }

    private ProblemTag(Tag tag) {
        setTag(tag);
    }

    public static ProblemTag create(Tag tag) {
        return new ProblemTag(tag);
    }

    /* 생성 메서드 */
    public static List<ProblemTag> create(List<TagCommand> tagCommands) {
        List<ProblemTag> problemTags = new ArrayList<>();
        for (TagCommand tagCommand : tagCommands)
            problemTags.add(ProblemTag.create(Tag.create(tagCommand)));

        return problemTags;
    }

    public static List<ProblemTag> create(Problem problem, List<TagCommand> tagCommands) {
        List<ProblemTag> problemTags = new ArrayList<>();
        for (TagCommand tagCommand : tagCommands) {
            ProblemTag problemTag = ProblemTag.create(Tag.create(tagCommand));
            problemTag.setProblem(problem);
        }

        return problemTags;
    }

}
