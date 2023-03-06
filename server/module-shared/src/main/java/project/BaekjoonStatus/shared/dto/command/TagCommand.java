package project.BaekjoonStatus.shared.dto.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagCommand {
    private Long tagId;
    private String name;
}
