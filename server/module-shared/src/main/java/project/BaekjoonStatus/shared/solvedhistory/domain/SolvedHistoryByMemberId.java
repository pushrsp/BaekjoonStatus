package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

@Getter
public class SolvedHistoryByMemberId {
    private String problemId;
    private String title;
    private Integer problemLevel;
    private List<Tag> tags;

    @Builder
    public SolvedHistoryByMemberId(String problemId, String title, Integer problemLevel, List<Tag> tags) {
        this.problemId = problemId;
        this.title = title;
        this.problemLevel = problemLevel;
        this.tags = tags;
    }

    public void addTags(List<Tag> tags) {
        this.tags = tags;
    }
}
