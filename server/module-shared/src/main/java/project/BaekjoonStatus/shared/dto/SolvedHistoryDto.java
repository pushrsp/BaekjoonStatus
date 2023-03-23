package project.BaekjoonStatus.shared.dto;

import lombok.*;

public class SolvedHistoryDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountByLevel {
        private String level;
        private Long count;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountByTag {
        private String tag;
        private Long count;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountByDate {
        private String day;
        private Long value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolvedHistoryByUserId {
        private Long problemId;
        private String title;
        private Integer level;
    }
}
