package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SolvedHistoryByUserId {
    private Long problemId;
    private String title;
    private Integer problemLevel;
    private List<Tag> tags;

    public void addTags(List<Tag> tags) {
        this.tags = tags;
    }
}
