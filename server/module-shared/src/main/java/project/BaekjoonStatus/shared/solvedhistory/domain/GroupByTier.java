package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GroupByTier implements Serializable {
    private String tier;
    private Long count;

    @Builder
    public GroupByTier(String tier, Long count) {
        this.tier = tier;
        this.count = count;
    }

    public static Map<String, Long> toMap(List<GroupByTier> groupByTiers) {
        return groupByTiers.stream()
                .collect(Collectors.toMap(GroupByTier::getTier, GroupByTier::getCount, Long::sum));
    }

    public static GroupByTier from(String tier, Long count) {
        return GroupByTier.builder()
                .tier(tier)
                .count(count)
                .build();
    }
}
