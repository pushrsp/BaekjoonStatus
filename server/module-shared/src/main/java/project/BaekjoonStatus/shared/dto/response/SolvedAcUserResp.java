package project.BaekjoonStatus.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SolvedAcUserResp {
    private String handle;
    private String bio;
    private List<Organization> organizations = new ArrayList<>();
    private Badge badge = new Badge();
    private Background background = new Background();
    private String profileImageUrl;
    private Long solvedCount;
    private Long voteCount;
    private Long exp;
    private Long tier;
    private Long rating;
    private Long ratingByClass;
    private Long ratingBySolvedCount;
    private Long ratingByVoteCount;
    @JsonProperty("class")
    private Long _class;
    private String classDecoration;
    private Long rivalCount;
    private Long reverseRivalCount;
    private Long maxStreak;
    private Long rank;
    private Boolean isRival;
    private boolean isReverseRival;

    @Data
    static class Organization {
        private Long organizationId;
        private String name;
        private String type;
        private Long rating;
        private Long userCount;
        private Long voteCount;
        private Long solvedCount;
        private String color;
    }

    @Data
    static class Badge {
        private String badgeId;
        private String badgeImageUrl;
        private String displayName;
        private String displayDescription;
    }

    @Data
    static class Background {
        private String backgroundId;
        private String backgroundImageUrl;
        private String author;
        private String authorUrl;
        private String displayName;
        private String displayDescription;
    }
}
