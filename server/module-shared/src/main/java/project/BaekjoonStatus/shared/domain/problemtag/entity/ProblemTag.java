package project.BaekjoonStatus.shared.domain.problemtag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PROBLEM_TAG")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemTag {
    @Id
    @GeneratedValue
    @Column(name = "problem_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id", columnDefinition = "BIGINT")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "tag_id", columnDefinition = "BIGINT")
    private Tag tag;

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /* 생성 메서드 */
    public static ProblemTag create(Tag tag) {
        ProblemTag problemTag = new ProblemTag();
        problemTag.setTag(tag);

        return problemTag;
    }

}
