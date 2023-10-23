package project.BaekjoonStatus.api.auth.service.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.service.DateService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RegisterTokenStoreTest {
    @DisplayName("problemIds와 localDate를 저장할 수 있다.")
    @Test
    public void can_store_problem_ids_and_local_date() throws Exception {
        //given
        RegisterTokenStore registerTokenStore = new RegisterTokenStore();
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(21);

        //when
        String key = registerTokenStore.put(problemIds, dateService);

        //then
        assertThat(key).isNotNull();
    }

    @DisplayName("key를 통해 registerToken을 가져올 수 있다.")
    @Test
    public void can_get_register_token_by_key() throws Exception {
        //given
        RegisterTokenStore registerTokenStore = new RegisterTokenStore();
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(23);

        String key = registerTokenStore.put(problemIds, dateService);

        //when
        RegisterToken token = registerTokenStore.get(key);

        //then
        assertThat(token).isNotNull();
        assertThat(token.getCreatedAt()).isEqualTo(dateService.getDate());
        assertThat(token.getProblemIds()).hasSize(problemIds.size());
    }

    @DisplayName("key값이 유효한 값인지 확인할 수 있다.")
    @Test
    public void can_detect_key_is_not_valid() throws Exception {
        //given
        RegisterTokenStore registerTokenStore = new RegisterTokenStore();
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(10);

        registerTokenStore.put(problemIds, dateService);

        //when
        RegisterToken token = registerTokenStore.get("should return null");

        //then
        assertThat(token).isNull();
    }

    @DisplayName("key값을 통해 registerToken을 삭제할 수 있다.")
    @Test
    public void can_remove_register_token_by_key() throws Exception {
        //given
        RegisterTokenStore registerTokenStore = new RegisterTokenStore();
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(5);

        String key = registerTokenStore.put(problemIds, dateService);

        //when
        registerTokenStore.remove(key);

        //then
        assertThat(registerTokenStore.get(key)).isNull();
    }

    @DisplayName("registerToken의 존재 여부를 알 수 있다.")
    @Test
    public void can_detect_register_token_is_existed_by_key() throws Exception {
        //given
        RegisterTokenStore registerTokenStore = new RegisterTokenStore();
        DateService dateService = dateService();
        List<String> problemIds = getProblemIds(5);

        String key = registerTokenStore.put(problemIds, dateService);

        //when
        boolean r1 = registerTokenStore.exist(key);
        boolean r2 = registerTokenStore.exist("should return false");

        //then
        assertThat(r1).isTrue();
        assertThat(r2).isFalse();
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
