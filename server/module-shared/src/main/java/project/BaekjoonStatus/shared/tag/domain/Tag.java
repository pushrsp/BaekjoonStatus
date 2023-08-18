package project.BaekjoonStatus.shared.tag.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Tag implements Serializable {
    private final String id;
    private final Problem problem;
    private final String tagName;

    @Builder
    private Tag(String id, Problem problem, String tagName) {
        this.id = id;
        this.problem = problem;
        this.tagName = tagName;
    }

    public static Tag from(String id, String tagName) {
        return Tag.builder()
                .id(id)
                .tagName(tagName)
                .build();
    }

    public static Map<String, List<Tag>> toMap(List<Tag> tags) {
        return tags.stream()
                .collect(Collectors.groupingBy(tag -> tag.getProblem().getId(), Collectors.toList()));
    }
}
