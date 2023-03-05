package project.BaekjoonStatus.shared.domain.problemtag.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PROBLEM_TAG")
@Data
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

}
