package project.BaekjoonStatus.api.service;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class RegisterToken {
    private LocalDate createdAt;
    private List<Long> solvedHistories;
}
