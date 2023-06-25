package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class GroupByTag implements Serializable {
    private String tag;
    private Integer count;
}
