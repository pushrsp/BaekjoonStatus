package project.BaekjoonStatus.shared.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.*;

@Getter
public class TagCommand {
    private String name;
    private UUID id;

    private TagCommand(String name) {
        this.name = name;
    }

    private TagCommand(UUID id) {
        this.id = id;
    }

    private TagCommand(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagCommand create(String name) {
        return new TagCommand(name);
    }

    public static TagCommand create(String name, UUID id) {
        return new TagCommand(id, name);
    }

    public static List<TagCommand> toCommandFromSolvedProblems(List<SolvedAcProblemResp> problemInfos) {
        List<TagCommand> tagCommands = new ArrayList<>();
        for (SolvedAcProblemResp info : problemInfos) {
            for (SolvedAcProblemResp.Tag tag : info.getTags())
                tagCommands.add(TagCommand.create(tag.getKey()));
        }

        return tagCommands;
    }

    public static Map<String, UUID> toCommandFromTagsIntoMap(List<Tag> tags) {
        Map<String, UUID> map = new HashMap<>();
        for (Tag tag : tags)
            map.put(tag.getName(), tag.getId());

        return map;
    }
}
