package project.BaekjoonStatus.shared.solvedac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SolvedAcAlias {
    private final String alias;

    @Builder
    private SolvedAcAlias(@JsonProperty("alias") String alias) {
        this.alias = alias;
    }
}
