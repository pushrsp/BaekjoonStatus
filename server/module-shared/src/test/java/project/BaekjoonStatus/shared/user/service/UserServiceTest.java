package project.BaekjoonStatus.shared.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.user.domain.User;
import project.BaekjoonStatus.shared.user.infra.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest extends IntegrationTestSupport {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("UserCreateServiceDto를 통해 user를 생성할 수 있다.")
    @Test
    public void can_create_user_from_UserCreateServiceDto() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 4, 13, 54);
        User userDomain = createUserDomain(now, "test");

        //when
        User user = userService.save(userDomain);

        //then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getCreatedTime()).isEqualTo(now);
        assertThat(user.getModifiedTime()).isEqualTo(now);
    }

    @DisplayName("username을 통해 user를 찾을 수 있다.")
    @Test
    public void can_find_user_by_username() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 4, 13, 54);
        String username = "test";
        User userDomain = createUserDomain(now, username);

        User savedUser = userService.save(userDomain);

        //when
        Optional<User> findUser = userService.findByUsername(username);

        //then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.get().getUsername()).isEqualTo(username);
    }

    @DisplayName("userId보다 큰 아이디를 가진 유저 목록을 가져올 수 있다.")
    @Test
    public void can_find_user_greater_than_given_userId() throws Exception {
        //given
        User firstUser = userService.save(createUserDomain(LocalDateTime.now(), "test333"));
        for (int i = 0; i < 5; i++) {
            userService.save(createUserDomain(LocalDateTime.now(), "test333" + i));
        }

        //when
        List<User> result1 = userService.findAllByGreaterThanUserId(firstUser.getId(), 10);
        List<User> result2 = userService.findAllByGreaterThanUserId(firstUser.getId() - 1, 10);
        List<User> result3 = userService.findAllByGreaterThanUserId(firstUser.getId() + 5, 2);

        //then
        assertThat(result1).hasSize(5);
        assertThat(result2).hasSize(6);
        assertThat(result3).hasSize(0);
    }

    private static User createUserDomain(LocalDateTime now, String username) {
        return User.builder()
                .username(username)
                .password("password")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .baekjoonUsername("baekjoon")
                .build();
    }
}
