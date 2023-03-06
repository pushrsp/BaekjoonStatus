package project.BaekjoonStatus.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SolvedAcProblemResp {
    private Long problemId;
    private String titleKo;
    private Boolean isSolvable;
    private Boolean isPartial;
    private Long acceptedUserCount;
    private Long level;
    private Long votedUserCount;
    private Boolean isLevelLocked;
    private Float averageTries;
    private List<Tag> tags = new ArrayList<>();

    @Data
    public static class Tag {
        private String key;
        private boolean isMeta;
        private Long bojTagId;
        private Long problemCount;
        private List<DisplayName> displayNames = new ArrayList<>();
        private List<Alias> aliases = new ArrayList<>();

        @Data
        public static class DisplayName {
            private String language;
            private String name;
            @JsonProperty("short")
            private String _short;
        }

        @Data
        public static class Alias {
            private String alias;
        }
    }
}
