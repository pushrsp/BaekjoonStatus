package project.BaekjoonStatus.api.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.DateService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class DateServiceTest {
    @DisplayName("오늘 날짜를 구할 수 있다.")
    @Test
    public void can_get_current_date() throws Exception {
        //given
        DateService dateService = new DateService() {
            @Override
            public LocalDate getDate() {
                return LocalDate.of(2023, 10, 18);
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
        LocalDate cur = LocalDate.of(2023, 10, 18);

        //when
        LocalDate result = dateService.getDate();

        //then
        assertThat(result).isEqualTo(cur);
    }

    @DisplayName("오늘 날짜와 시간을 구할 수 있다.")
    @Test
    public void can_get_current_date_time() throws Exception {
        //given
        DateService dateService = new DateService() {
            @Override
            public LocalDate getDate() {
                return null;
            }

            @Override
            public LocalDate getToday(LocalDateTime now) {
                return null;
            }

            @Override
            public LocalDateTime getDateTime() {
                return LocalDateTime.of(2023, 10, 18, 0, 0, 0);
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
        LocalDateTime cur = LocalDateTime.of(2023, 10, 18, 0, 0, 0);

        //when
        LocalDateTime result = dateService.getDateTime();

        //then
        assertThat(result).isEqualTo(cur);
    }

    @DisplayName("cache용 날짜를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideDateTime")
    public void can_get_next_cache_key(LocalDateTime now, LocalDateTime expected) throws Exception {
        //given
        DateService dateService = new KrDateService();

        //when
        LocalDateTime nextCacheKey = dateService.getNextCacheKey(now);

        //then
        assertThat(nextCacheKey).isEqualTo(expected);
    }

    @DisplayName("오늘의 문제 조회용 날짜를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideToday")
    public void can_get_today_for_daily_problem(LocalDateTime now, LocalDate expected) throws Exception {
        //given
        DateService dateService = new KrDateService();

        //when
        LocalDate today = dateService.getToday(now);

        //then
        assertThat(today).isEqualTo(expected);
    }

    private static Stream<Arguments> provideDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2023, 10, 18, 5, 9, 30), LocalDateTime.of(2023, 10, 18, 5, 10, 0)),
                Arguments.of(LocalDateTime.of(2023, 10, 18, 5, 10, 0), LocalDateTime.of(2023, 10, 19, 5, 10, 0))
        );
    }

    private static Stream<Arguments> provideToday() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2023, 10, 18, 5, 9, 30), LocalDate.of(2023, 10, 17)),
                Arguments.of(LocalDateTime.of(2023, 10, 18, 5, 10, 0), LocalDate.of(2023, 10, 18))
        );
    }
}
