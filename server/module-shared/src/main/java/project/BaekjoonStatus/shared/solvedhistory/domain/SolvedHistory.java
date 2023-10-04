package project.BaekjoonStatus.shared.solvedhistory.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SolvedHistory {
    private final String id;
    private final Member member;
    private final Problem problem;
    private final Boolean isBefore;
    private final Integer problemLevel;
    private final LocalDate createdDate;
    private final LocalDateTime createdTime;

    @Builder
    private SolvedHistory(String id, Member member, Problem problem, Boolean isBefore, Integer problemLevel, LocalDate createdDate, LocalDateTime createdTime) {
        this.id = id;
        this.member = member;
        this.problem = problem;
        this.isBefore = isBefore;
        this.problemLevel = problemLevel;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    private static SolvedHistory from(Member member, Problem problem, boolean isBefore, LocalDateTime createdTime, LocalDate createdDate) {
        return SolvedHistory.builder()
                .member(member)
                .problem(problem)
                .isBefore(isBefore)
                .problemLevel(problem.getLevel())
                .createdTime(createdTime)
                .createdDate(createdDate)
                .build();
    }

    public static List<SolvedHistory> from(Member member, List<Problem> problems, boolean isBefore, DateService dateService) {
        LocalDateTime createdTime = dateService.getDateTime();
        LocalDate createdDate = createdTime.toLocalDate();

        return problems.stream()
                .map(p -> SolvedHistory.from(member, p, isBefore, createdTime, createdDate))
                .collect(Collectors.toList());
    }
}
