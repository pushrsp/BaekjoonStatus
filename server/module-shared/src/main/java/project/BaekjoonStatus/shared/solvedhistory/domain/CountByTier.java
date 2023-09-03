package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class CountByTier {
    private String tier;
    private Long count;

    @Builder
    public CountByTier(String tier, Long count) {
        this.tier = tier;
        this.count = count;
    }

    public static Map<String, Long> toMap(List<CountByTier> groupByTiers) {
        return groupByTiers.stream()
                .collect(Collectors.toMap(CountByTier::getTier, CountByTier::getCount, Long::sum));
    }
}
