package project.BaekjoonStatus.shared.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.infra.UserRepository;
import project.BaekjoonStatus.shared.user.service.request.UserCreateServiceDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FindByUsernameParameterizedTest extends IntegrationTestSupport {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            UserCreateServiceDto userCreateServiceDto = createUserCreateServiceDto(LocalDateTime.now(), "test" + i);
            userService.save(userCreateServiceDto);
        }
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("username 인자는 소문자와 숫자로만 이루어져야 한다.")
    @ParameterizedTest
    @MethodSource("provideUsername")
    public void can_detect_username_has_valid_format(String username, boolean expected) throws Exception {
        //when
        Optional<User> findUser = userService.findByUsername(username);

        //then
        assertThat(findUser.isEmpty()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideUsername() {
        return Stream.of(
                Arguments.of("", true),
                Arguments.of("$#abc", true),
                Arguments.of(null, true),
                Arguments.of("test123", true),
                Arguments.of("test3", false)
        );
    }

    private static UserCreateServiceDto createUserCreateServiceDto(LocalDateTime now, String username) {
        return UserCreateServiceDto.builder()
                .username(username)
                .password("password")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .baekjoonUsername("baekjoon")
                .build();
    }
}
