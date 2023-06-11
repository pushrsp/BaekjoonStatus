package project.BaekjoonStatus.shared.tag.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;

@Getter
public class Tag {
    private final String id;
    private final Problem problem;
    private final String tagName;

    @Builder
    public Tag(String id, Problem problem, String tagName) {
        this.id = id;
        this.problem = problem;
        this.tagName = tagName;
    }
}
