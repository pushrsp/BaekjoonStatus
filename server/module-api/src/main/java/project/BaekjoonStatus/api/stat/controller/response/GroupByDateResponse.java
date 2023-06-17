package project.BaekjoonStatus.api.stat.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByDate;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GroupByDateResponse {
    private String day;
    private Long value;

    public GroupByDateResponse(String day, Long value) {
        this.day = day;
        this.value = value;
    }

    public static GroupByDateResponse from(GroupByDate groupByDate) {
        return GroupByDateResponse.builder()
                .day(groupByDate.getDay().toString())
                .value(groupByDate.getCount())
                .build();
    }

    public static List<GroupByDateResponse> from(List<GroupByDate> groupByDates) {
        return groupByDates.stream()
                .map(GroupByDateResponse::from)
                .collect(Collectors.toList());
    }
}
