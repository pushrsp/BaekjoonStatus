package project.BaekjoonStatus.api.dto;

import lombok.*;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.CountByLevel;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class StatDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyCountDto {
        @NotEmpty(message = "년도를 입력해주세요.")
        private String year;
    }

    @Data
    @Builder
    public static class CountByLevelResp {
        private List<CountByLevel> countByLevels;
    }
}
