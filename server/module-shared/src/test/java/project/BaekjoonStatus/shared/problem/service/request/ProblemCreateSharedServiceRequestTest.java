package project.BaekjoonStatus.shared.problem.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ProblemCreateSharedServiceRequestTest {
    @DisplayName("멤버 필드가 올바른 값인지 확인 가능하다.")
    @ParameterizedTest
    @MethodSource("provideFields")
    public void can_detect_field_is_invalid(String id, Integer level, String title, LocalDateTime createdTime, String expected) throws Exception {
        //given
        ProblemCreateSharedServiceRequest request = ProblemCreateSharedServiceRequest.builder()
                .id(id)
                .level(level)
                .title(title)
                .createdTime(createdTime)
                .build();

        //when
        IllegalStateException illegalStateException = catchThrowableOfType(() -> request.toDomain(), IllegalStateException.class);

        //then
        assertThat(illegalStateException.getMessage()).isEqualTo(expected);
    }

    @DisplayName("중복된 id를 감지할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideIds")
    public void can_detect_duplicate_in_list_of_ProblemCreateSharedServiceRequest(List<String> ids, boolean expected) throws Exception {
        //given
        List<ProblemCreateSharedServiceRequest> requests = ids.stream()
                .map(this::createProblemCreateSharedServiceRequest)
                .collect(Collectors.toList());

        //when
        boolean result = ProblemCreateSharedServiceRequest.hasDuplicateId(requests);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIds() {
        return Stream.of(
                Arguments.of(List.of("1000", "10000", "2000"), false),
                Arguments.of(List.of("1000", "1000", "2000"), true)
        );
    }

    private static Stream<Arguments> provideFields() {
        return Stream.of(
                Arguments.of(null, 0, "title", LocalDateTime.now(), "id를 입력해주세요."),
                Arguments.of("1", null, "title", LocalDateTime.now(), "level을 입력해주세요."),
                Arguments.of("1", -1, "title", LocalDateTime.now(), "level은 0~70 사이의 범위만 가져야 합니다."),
                Arguments.of("1", 71, "title", LocalDateTime.now(), "level은 0~70 사이의 범위만 가져야 합니다."),
                Arguments.of("1", "2", null, LocalDateTime.now(), "title을 입력해주세요."),
                Arguments.of("2", 0, "title", null, "createdTime을 입력해주세요.")
        );
    }

    private ProblemCreateSharedServiceRequest createProblemCreateSharedServiceRequest(String id) {
        return ProblemCreateSharedServiceRequest.builder()
                .id(id)
                .level(1)
                .title("title")
                .createdTime(LocalDateTime.now())
                .build();
    }
}
