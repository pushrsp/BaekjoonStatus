package project.BaekjoonStatus.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class SolvedHistoryDto {
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolvedHistoriesReq {
        private String userId;
        private Integer offset;

        public String getUserId() { return this.userId;}

        public int getOffset() {
            return this.offset == null ? 0 : this.offset;
        }
    }
}
