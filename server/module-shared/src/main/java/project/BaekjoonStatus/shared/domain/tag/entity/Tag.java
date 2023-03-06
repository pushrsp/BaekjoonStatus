package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

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

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public Tag( String name) {
        this.name = name;
    }

    /* 생성 메서드 */
    public static Tag create(SolvedAcProblemResp.Tag problemTag) {
        return new Tag(problemTag.getKey());
    }
}
