package project.BaekjoonStatus.shared.domain.tag.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import project.BaekjoonStatus.shared.dto.command.TagCommand;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    public static Tag create(TagCommand tagCommand) {
        return new Tag(tagCommand.getId(), tagCommand.getName());
    }

    /* 생성 메서드 */
    public static List<Tag> create(List<TagCommand> tagCommands) {
        List<Tag> tags = new ArrayList<>();
        for (TagCommand tagCommand : tagCommands)
            tags.add(Tag.create(tagCommand.getName()));

        return tags;
    }

    public static List<Tag> createByTagNames(List<String> tagNames) {
        return tagNames.stream()
                .map(Tag::create)
                .toList();
    }
}
