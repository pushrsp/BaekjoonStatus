package project.BaekjoonStatus.api.stat.controller.response;

import lombok.Builder;
import lombok.Data;
import project.BaekjoonStatus.shared.dailyproblem.domain.DailyProblem;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TodayProblemsResponse {
    private String problemId;
    private String title;
    private Integer problemLevel;

    public static TodayProblemsResponse from(DailyProblem dailyProblem) {
        return TodayProblemsResponse.builder()
                .problemId(dailyProblem.getProblem().getId())
                .title(dailyProblem.getProblem().getTitle())
                .problemLevel(dailyProblem.getProblem().getLevel())
                .build();
    }

    public static List<TodayProblemsResponse> from(List<DailyProblem> dailyProblems) {
        return dailyProblems.stream()
                .map(TodayProblemsResponse::from)
                .collect(Collectors.toList());
    }
}
