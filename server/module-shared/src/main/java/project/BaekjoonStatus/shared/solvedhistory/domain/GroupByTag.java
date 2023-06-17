package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GroupByTag {
    private String tag;
    private Integer count;
}
