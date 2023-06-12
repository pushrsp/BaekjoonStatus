package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BaekjoonVerifyResponse {
    private int solvedCount;
    private String registerToken;

    public static BaekjoonVerifyResponse from(List<Long> problems, String registerToken) {
        return BaekjoonVerifyResponse.builder()
                .solvedCount(problems.size())
                .registerToken(registerToken)
                .build();
    }
}
