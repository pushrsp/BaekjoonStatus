package project.BaekjoonStatus.shared.dto;

import lombok.*;

public class SolvedHistoryDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SolvedHistoryResp {
        private Long problemId;
        private String title;
        private Integer level;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SolvedCountByDate {
        private String date;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SolvedCountByLevel {
        private Integer level;
        private Long count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SolvedCountByTag {
        private String tag;
        private Long count;
    }
}
