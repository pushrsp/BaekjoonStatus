package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetSolvedCountGroupByDateCommand {
    private String userId;
    private String year;

    public UUID getUUIDUserId() {
        return UUID.fromString(this.userId);
    }
}
