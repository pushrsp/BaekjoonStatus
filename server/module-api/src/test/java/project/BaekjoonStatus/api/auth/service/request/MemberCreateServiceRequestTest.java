package project.BaekjoonStatus.api.auth.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import project.BaekjoonStatus.shared.common.service.PasswordService;
import project.BaekjoonStatus.shared.member.service.request.MemberCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemberCreateServiceRequestTest {
    @DisplayName("SharedServieRequest로 변환할 수 있다.")
    @Test
    public void can_convert_to_shared_service_request() throws Exception {
        //given
        PasswordService passwordService = getPasswordService();
        LocalDateTime now = LocalDateTime.of(2023, 11, 21, 11, 49, 9);
        MemberCreateServiceRequest memberCreateServiceRequest = MemberCreateServiceRequest.builder()
                .username("username1234")
                .password("password4321")
                .baekjoonUsername("baekjoon")
                .createdTime(now)
                .modifiedTime(now)
                .build();

        //when
        MemberCreateSharedServiceRequest result = memberCreateServiceRequest.toRequest(true, passwordService);

        //then
        assertThat(result.getPassword()).isEqualTo(memberCreateServiceRequest.getPassword());
        assertThat(result.getCreatedTime()).isEqualTo(memberCreateServiceRequest.getCreatedTime());
        assertThat(result.getModifiedTime()).isEqualTo(memberCreateServiceRequest.getModifiedTime());
    }

    private PasswordService getPasswordService() {
        return new PasswordService() {
            @Override
            public String hashPassword(String plainPassword) {
                return plainPassword;
            }

            @Override
            public boolean validatePassword(String plainPassword, String hashedPassword) {
                return false;
            }
        };
    }

    @DisplayName("아이디와 비밀번호가 유효한지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideUsernameAndPassword")
    public void can_detect_whether_username_and_password_are_valid(String username, String password, String expectedMessage) throws Exception {
        //given
        PasswordService passwordService = getPasswordService();
        LocalDateTime now = LocalDateTime.of(2023, 11, 21, 11, 49, 9);
        MemberCreateServiceRequest memberCreateServiceRequest = MemberCreateServiceRequest.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("baekjoon")
                .createdTime(now)
                .modifiedTime(now)
                .build();


        //when
        IllegalStateException illegalStateException = catchThrowableOfType(() -> memberCreateServiceRequest.toRequest(true, passwordService), IllegalStateException.class);

        //then
        assertThat(illegalStateException.getMessage()).isEqualTo(expectedMessage);
    }

    private static Stream<Arguments> provideUsernameAndPassword() {
        return Stream.of(
                Arguments.of("aaa1", "fdsaf", "최소 5자 최대 15자 이내여야 합니다."),
                Arguments.of("aaa1fsdadfsa3241", "fdsaf", "최소 5자 최대 15자 이내여야 합니다."),
                Arguments.of("aaa1@@", "fdsaf", "아이디는 영어와 숫자로만 이루어져야 합니다."),
                Arguments.of("koko1234", "123ffdsa2@@fdsasavxzvsdfa@@@@fd43", "최소 8자 최대 20자 이내여야 합니다.")
        );
    }
}
