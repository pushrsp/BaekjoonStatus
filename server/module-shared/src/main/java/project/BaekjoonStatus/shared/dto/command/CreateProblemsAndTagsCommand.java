package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.dto.response.SolvedAcProblemResp;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CreateProblemsAndTagsCommand {
    private List<SolvedAcProblemResp> problemInfos;

    public List<SolvedAcProblemResp.Tag> getTags() {
        List<SolvedAcProblemResp.Tag> tags = new ArrayList<>();
        for (SolvedAcProblemResp problemInfo : getProblemInfos())
            tags.addAll(problemInfo.getTags());

        return tags;
    }
}
