package project.BaekjoonStatus.shared.domain.problemtag.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PROBLEM_TAG")
@Data
public class ProblemTag {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "problem_tag_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(name = "tag_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID tagId;

}
