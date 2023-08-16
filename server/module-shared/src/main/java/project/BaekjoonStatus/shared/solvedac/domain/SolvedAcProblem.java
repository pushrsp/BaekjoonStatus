package project.BaekjoonStatus.shared.solvedac.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.time.LocalDateTime;
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
    private SolvedAcProblem(@JsonProperty("problemId") Long problemId,
                           @JsonProperty("titleKo") String titleKo,
                           @JsonProperty("isSolvable") Boolean isSolvable,
                           @JsonProperty("isPartial") Boolean isPartial,
                           @JsonProperty("acceptedUserCount") Long acceptedUserCount,
                           @JsonProperty("level") Long level,
                           @JsonProperty("votedUserCount") Long votedUserCount,
                           @JsonProperty("isLevelLocked") Boolean isLevelLocked,
                           @JsonProperty("averageTries") Float averageTries,
                           @JsonProperty("tags") List<SolvedAcTag> tags
    ) {
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

    public static List<Problem> toProblemList(List<SolvedAcProblem> solvedAcProblems, LocalDateTime createdTime) {
        return solvedAcProblems.stream()
                .map(sp -> sp.toDomain(createdTime))
                .collect(Collectors.toList());
    }

    public static List<Tag> toTagList(List<SolvedAcProblem> solvedAcProblems, LocalDateTime createdTime) {
        return solvedAcProblems.stream()
                .flatMap(sp -> sp.getTags().stream().map(tag -> tag.toDomain(sp.toDomain(createdTime))))
                .collect(Collectors.toList());
    }

    public Problem toDomain(LocalDateTime createdTime) {
        return Problem.builder()
                .id(this.problemId)
                .level(this.level.intValue())
                .title(this.titleKo)
                .createdTime(createdTime)
                .build();
    }

    public List<Tag> toTagList(Problem problem) {
        return this.tags.stream()
                .map(t -> t.toDomain(problem))
                .collect(Collectors.toList());
    }
}
