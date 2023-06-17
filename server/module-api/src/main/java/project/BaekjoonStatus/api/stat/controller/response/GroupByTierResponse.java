package project.BaekjoonStatus.api.stat.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.solvedhistory.domain.GroupByTier;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GroupByTierResponse {
    private String level;
    private Long count;

    public GroupByTierResponse(String level, Long count) {
        this.level = level;
        this.count = count;
    }

    public static GroupByTierResponse from(GroupByTier groupByTier) {
        return GroupByTierResponse.builder()
                .level(groupByTier.getTier())
                .count(groupByTier.getCount())
                .build();
    }

    public static List<GroupByTierResponse> from(List<GroupByTier> groupByTiers) {
        return groupByTiers.stream()
                .map(GroupByTierResponse::from)
                .collect(Collectors.toList());
    }
}
