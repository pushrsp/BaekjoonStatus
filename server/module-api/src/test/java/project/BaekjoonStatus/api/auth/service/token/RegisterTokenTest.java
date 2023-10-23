package project.BaekjoonStatus.api.auth.service.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.DateService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RegisterTokenTest {
    @DisplayName("localDate와 problemIds를 통해 registerToken을 만들 수 있다.")
    @ParameterizedTest
    @MethodSource("provideNumOfProblemId")
    public void can_generate_token_by_local_date_and_problem_ids(int numOfProblemId) throws Exception {
        //given
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(numOfProblemId);

        //when
        RegisterToken token = RegisterToken.builder()
                .createdAt(dateService.getDate())
                .problemIds(problemIds)
                .build();

        //then
        assertThat(token.getCreatedAt()).isEqualTo(dateService.getDate());
        assertThat(token.getProblemIds()).hasSize(numOfProblemId);
    }

    private static Stream<Arguments> provideNumOfProblemId() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(100)
        );
    }

    private List<String> getProblemIds(int numOfProblemId) {
        List<String> ret = new ArrayList<>();
        for (int i = 1; i <= numOfProblemId; i++) {
            ret.add(String.valueOf(i));
        }

        return ret;
    }

    private DateService dateService() {
        return new DateService() {
            @Override
            public LocalDate getDate() {
                return LocalDate.of(2023, 10, 23);
            }

            @Override
            public LocalDate getToday(LocalDateTime now) {
                return null;
            }

            @Override
            public LocalDateTime getDateTime() {
                return null;
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
