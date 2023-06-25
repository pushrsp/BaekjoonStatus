package project.BaekjoonStatus.shared.tag.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Tag implements Serializable {
    private final String id;
    private final Problem problem;
    private final String tagName;

    @Builder
    public Tag(String id, Problem problem, String tagName) {
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

    public static Map<Long, List<Tag>> toMap(List<Tag> tags) {
        Map<Long, List<Tag>> ret = new HashMap<>();
        for (Tag tag : tags) {
            if(!ret.containsKey(tag.getProblem().getId())) {
                List<Tag> temp = new ArrayList<>();
                temp.add(Tag.from(tag.getId(), tag.getTagName()));

                ret.put(tag.getProblem().getId(), temp);
            } else {
                ret.get(tag.getProblem().getId()).add(Tag.from(tag.getId(), tag.getTagName()));
            }
        }

        return ret;
    }
}
