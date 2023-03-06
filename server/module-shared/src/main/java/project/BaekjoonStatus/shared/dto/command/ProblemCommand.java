package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProblemCommand {
    private Long problemId;
    private int level;
    private String title;
    private List<TagCommand> tags;
}
