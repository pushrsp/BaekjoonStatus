package project.BaekjoonStatus.shared.solvedac.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SolvedAcAlias {
    private final String alias;

    @Builder
    public SolvedAcAlias(String alias) {
        this.alias = alias;
    }
}
