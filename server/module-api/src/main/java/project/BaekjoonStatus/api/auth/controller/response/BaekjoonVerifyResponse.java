package project.BaekjoonStatus.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BaekjoonVerifyResponse {
    private int solvedCount;
    private String registerToken;

    @Builder
    private BaekjoonVerifyResponse(int solvedCount, String registerToken) {
        this.solvedCount = solvedCount;
        this.registerToken = registerToken;
    }

    public static BaekjoonVerifyResponse from(List<Long> problems, String registerToken) {
        return BaekjoonVerifyResponse.builder()
                .solvedCount(problems.size())
                .registerToken(registerToken)
                .build();
    }
}
