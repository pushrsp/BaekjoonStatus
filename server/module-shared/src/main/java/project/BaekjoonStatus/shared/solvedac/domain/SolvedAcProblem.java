package project.BaekjoonStatus.shared.solvedac.domain;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.common.utils.DateProvider;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SolvedAcProblem {
    private final Long problemId;
    private final String titleKo;
    private final Boolean isSolvable;
    private final Boolean isPartial;
    private final Long acceptedUserCount;
    private final Long level;
    private final Long votedUserCount;
    private final Boolean isLevelLocked;
    private final Float averageTries;
    private final List<SolvedAcTag> tags;

    @Builder
    public SolvedAcProblem(Long problemId, String titleKo, Boolean isSolvable, Boolean isPartial, Long acceptedUserCount, Long level, Long votedUserCount, Boolean isLevelLocked, Float averageTries, List<SolvedAcTag> tags) {
        this.problemId = problemId;
        this.titleKo = titleKo;
        this.isSolvable = isSolvable;
        this.isPartial = isPartial;
        this.acceptedUserCount = acceptedUserCount;
        this.level = level;
        this.votedUserCount = votedUserCount;
        this.isLevelLocked = isLevelLocked;
        this.averageTries = averageTries;
        this.tags = tags;
    }

    public static List<Problem> toProblemList(List<SolvedAcProblem> solvedAcProblems) {
        return solvedAcProblems.stream()
                .map(SolvedAcProblem::to)
                .collect(Collectors.toList());
    }

    public static List<Tag> toTagList(List<SolvedAcProblem> solvedAcProblems) {
        List<Tag> ret = new ArrayList<>();
        for (SolvedAcProblem solvedAcProblem : solvedAcProblems) {
            Problem problem = solvedAcProblem.to();
            for (SolvedAcTag tag : solvedAcProblem.getTags()) {
                ret.add(tag.to(problem));
            }
        }

        return ret;
    }

    public Problem to() {
        return Problem.builder()
                .id(this.problemId)
                .level(this.level.intValue())
                .title(this.titleKo)
                .createdTime(DateProvider.getDateTime())
                .build();
    }
}
