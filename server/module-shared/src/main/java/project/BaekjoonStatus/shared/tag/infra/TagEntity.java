package project.BaekjoonStatus.shared.tag.infra;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;
import project.BaekjoonStatus.shared.problem.infra.ProblemEntity;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TAG", indexes = {
        @Index(name = "idx__tag_name", columnList = "tag_name")
})
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

    public static TagEntity from(Tag tag) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.problem = ProblemEntity.from(tag.getProblem());
        tagEntity.tagName = tag.getTagName();

        return tagEntity;
    }

    public Tag to() {
        return Tag.builder()
                .id(this.id.toString())
                .problem(this.problem.to())
                .tagName(this.tagName)
                .build();
    }
}
