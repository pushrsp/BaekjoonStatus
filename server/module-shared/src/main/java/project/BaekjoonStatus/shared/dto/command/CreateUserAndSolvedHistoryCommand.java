package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateUserAndSolvedHistoryCommand {
    private String username;
    private String password;
    private String baekjoonUsername;
    private Boolean isBefore;
    private List<Long> solvedHistories;
}
