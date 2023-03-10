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
}
