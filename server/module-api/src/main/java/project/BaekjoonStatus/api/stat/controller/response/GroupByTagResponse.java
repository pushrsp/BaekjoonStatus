package project.BaekjoonStatus.api.stat.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTag;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GroupByTagResponse {
    private String tag;
    private Integer count;

    public GroupByTagResponse(String tag, Integer count) {
        this.tag = tag;
        this.count = count;
    }

    public static GroupByTagResponse from(GroupByTag groupByTag) {
        return GroupByTagResponse.builder()
                .tag(groupByTag.getTag())
                .count(groupByTag.getCount())
                .build();
    }

    public static List<GroupByTagResponse> from(List<GroupByTag> groupByTags) {
        return groupByTags.stream()
                .map(GroupByTagResponse::from)
                .collect(Collectors.toList());
    }
}
