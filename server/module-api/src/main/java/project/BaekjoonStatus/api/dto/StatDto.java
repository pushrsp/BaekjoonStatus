package project.BaekjoonStatus.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto;
import project.BaekjoonStatus.shared.dto.SolvedHistoryDto.SolvedCountByDate;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolvedCountDto {
        @NotEmpty(message = "접근할 수 없습니다.")
        private String userId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolvedCountByDateDto {
        private Map<String, Long> result = new HashMap<>();

        public static SolvedCountByDateDto of(List<SolvedCountByDate> data) {
            SolvedCountByDateDto dto = new SolvedCountByDateDto();
            for (SolvedCountByDate d : data)
                dto.getResult().put(d.getDate(), d.getCount());

            return dto;
        }
    }
}
