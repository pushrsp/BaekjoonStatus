package project.BaekjoonStatus.shared.solvedac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

@Data
public class SolvedAcTag {
    private final String key;
    private final Boolean isMeta;
    private final Long bojTagId;
    private final Long problemCount;
    private final List<SolvedAcDisplayName> displayNames;
    private final List<SolvedAcAlias> aliases;

    @Builder
    public SolvedAcTag(@JsonProperty("key") String key,
                       @JsonProperty("isMeta") Boolean isMeta,
                       @JsonProperty("bojTagId") Long bojTagId,
                       @JsonProperty("problemCount") Long problemCount,
                       @JsonProperty("displayNames") List<SolvedAcDisplayName> displayNames,
                       @JsonProperty("aliases") List<SolvedAcAlias> aliases
    ) {
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
