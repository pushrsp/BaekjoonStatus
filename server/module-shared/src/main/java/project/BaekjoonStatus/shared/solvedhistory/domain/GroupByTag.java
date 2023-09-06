package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class GroupByTag implements Serializable {
    private String tag;
    private Integer count;

    @Builder
    private GroupByTag(String tag, Integer count) {
        this.tag = tag;
        this.count = count;
    }
}
