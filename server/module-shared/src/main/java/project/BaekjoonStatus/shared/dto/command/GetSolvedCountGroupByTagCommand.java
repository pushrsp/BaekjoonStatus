package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetSolvedCountGroupByTagCommand {
    private String userId;

    public UUID getUUIDUserId() {
        return UUID.fromString(this.userId);
    }
}
