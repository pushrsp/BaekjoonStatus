package project.BaekjoonStatus.shared.solvedac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class SolvedAcDisplayName {
    private final String language;
    private final String name;
    private final String _short;

    @Builder
    public SolvedAcDisplayName(@JsonProperty("language") String language, @JsonProperty("name") String name, @JsonProperty("short") String _short) {
        this.language = language;
        this.name = name;
        this._short = _short;
    }
}