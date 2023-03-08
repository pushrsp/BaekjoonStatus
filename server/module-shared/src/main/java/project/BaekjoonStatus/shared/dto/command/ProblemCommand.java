package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProblemCommand {
    private Long problemId;
    private int level;
    private String title;
    private List<TagCommand> tags;

    private ProblemCommand(Long problemId, int level, String title, List<TagCommand> tags) {
        this.problemId = problemId;
        this.level = level;
        this.title = title;
        this.tags = tags;
    }

    public static ProblemCommand create(SolvedAcProblemResp info, List<TagCommand> tags) {
        return new ProblemCommand(info.getProblemId(), info.getLevel().intValue(), info.getTitleKo(), tags);
    }

    public static List<ProblemCommand> toCommandFromSolvedProblemsAndTags(List<SolvedAcProblemResp> problemInfos, Map<String, UUID> tags) {
        List<ProblemCommand> problemCommands = new ArrayList<>();
        for (SolvedAcProblemResp info : problemInfos) {
            List<TagCommand> tagCommands = new ArrayList<>();
            for (SolvedAcProblemResp.Tag tag : info.getTags())
                tagCommands.add(TagCommand.create(tag.getKey(), tags.get(tag.getKey())));

            problemCommands.add(ProblemCommand.create(info, tagCommands));
        }

        return problemCommands;
    }
}
