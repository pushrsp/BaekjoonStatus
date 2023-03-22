package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
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

    @Column(name = "name", nullable = false)
    private String name;

    private Tag(String name) {
        this.name = name;
    }

    private Tag(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Tag create(String name) {
        return new Tag(name);
    }

    public static Tag create(UUID id, String name) {
        return new Tag(id, name);
    }

    /* 생성 메서드 */
    public static List<Tag> createByNames(Set<String> names) {
        return names.stream()
                .map(Tag::create)
                .toList();
    }

    public static List<Tag> createByNames(List<String> tagNames) {
        return tagNames.stream()
                .map(Tag::create)
                .toList();
    }
}
