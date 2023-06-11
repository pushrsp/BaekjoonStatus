package project.BaekjoonStatus.shared.solvedac.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

@Getter
public class SolvedAcTag {
    private final String key;
    private final boolean isMeta;
    private final Long bojTagId;
    private final Long problemCount;
    private final List<SolvedAcDisplayName> displayNames;
    private final List<SolvedAcAlias> aliases;

    @Builder
    public SolvedAcTag(String key, boolean isMeta, Long bojTagId, Long problemCount, List<SolvedAcDisplayName> displayNames, List<SolvedAcAlias> aliases) {
        this.key = key;
        this.isMeta = isMeta;
        this.bojTagId = bojTagId;
        this.problemCount = problemCount;
        this.displayNames = displayNames;
        this.aliases = aliases;
    }

    public Tag to(Problem problem) {
        return Tag.builder()
                .tagName(this.key)
                .problem(problem)
                .build();
    }
}
