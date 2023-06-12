package project.BaekjoonStatus.shared.solvedac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvedAcAlias {
    private final String alias;

    @Builder
    public SolvedAcAlias(@JsonProperty("alias") String alias) {
        this.alias = alias;
    }
}
