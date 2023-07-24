package project.BaekjoonStatus.shared.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class DateProviderTest {

    @DisplayName("05:10AM 시점을 기준으로 캐시 키값을 가져온다.")
    @ParameterizedTest
    @MethodSource("provideLocalDateTime")
    public void generate_cache_key_based_on_05_10(LocalDateTime now, LocalDateTime expected) throws Exception {
        //when
        LocalDateTime nextCacheKey = DateProvider.getNextCacheKey(now);

        //then
        assertThat(nextCacheKey).isEqualToIgnoringSeconds(expected);
    }

    private static Stream<Arguments> provideLocalDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2023, 5, 1, 5, 10), LocalDateTime.of(2023, 5, 2, 5, 10)),
                Arguments.of(LocalDateTime.of(2023, 5, 1, 5, 9), LocalDateTime.of(2023, 5, 1, 5, 10)),
                Arguments.of(LocalDateTime.of(2023, 5, 1, 20, 0), LocalDateTime.of(2023, 5, 2, 5, 10))
        );
    }
}
