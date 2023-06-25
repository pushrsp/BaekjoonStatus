package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Long> ret = new HashMap<>();
        for (GroupByTier groupByTier : groupByTiers) {
            if(!ret.containsKey(groupByTier.getTier())) {
                ret.put(groupByTier.getTier(), groupByTier.getCount());
            } else {
                ret.replace(groupByTier.getTier(), ret.get(groupByTier.getTier()) + groupByTier.getCount());
            }
        }

        return ret;
    }

    public static GroupByTier from(String tier, Long count) {
        return GroupByTier.builder()
                .tier(tier)
                .count(count)
                .build();
    }
}
