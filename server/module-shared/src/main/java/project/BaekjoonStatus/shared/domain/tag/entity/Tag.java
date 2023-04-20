package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;

import javax.persistence.*;
import java.util.UUID;

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

    private Tag(Problem problem, String tagName) {
        this.problem = problem;
        this.tagName = tagName;
    }

    /* 생성 메서드 */
    public static Tag create(Problem problem, String tagName) {
        return new Tag(problem, tagName);
    }
}
