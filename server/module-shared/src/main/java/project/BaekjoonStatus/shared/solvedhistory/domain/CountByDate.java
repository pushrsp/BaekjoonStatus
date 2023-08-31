package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CountByDate {
    private LocalDate day;
    private Long count;

    //querydsl 생성자
    public CountByDate(LocalDate day, Long count) {
        this.day = day;
        this.count = count;
    }
}
