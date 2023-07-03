package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class GroupByDate implements Serializable {
    private LocalDate day;
    private Long count;

    public GroupByDate(LocalDate day, Long count) {
        this.day = day;
        this.count = count;
    }
}