package project.BaekjoonStatus.shared.member.service.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberCreateSharedServiceRequestTest {
    @DisplayName("MemberCreateSharedServiceRequest는 member 도메인으로 컨버팅 할 수 있다.")
    @Test
    public void can_convert_to_member_domain() throws Exception {
        //given
        MemberCreateSharedServiceRequest request = MemberCreateSharedServiceRequest.builder()
                .username("username")
                .password("password")
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(getDateService().getDateTime())
                .modifiedTime(getDateService().getDateTime())
                .build();

        //when
        Member memberDomain = request.toDomain();

        //then
        assertThat(memberDomain.getId()).isNull();
        assertThat(memberDomain.getUsername()).isEqualTo(request.getUsername());
        assertThat(memberDomain.getPassword()).isEqualTo(request.getPassword());
        assertThat(memberDomain.getBaekjoonUsername()).isEqualTo(request.getBaekjoonUsername());
        assertThat(memberDomain.getIsPrivate()).isEqualTo(request.getIsPrivate());
        assertThat(memberDomain.getCreatedTime()).isEqualTo(request.getCreatedTime());
        assertThat(memberDomain.getModifiedTime()).isEqualTo(request.getModifiedTime());
    }

    private DateService getDateService() {
        return new DateService() {
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
                return LocalDateTime.of(2023, 8, 16, 17, 48);
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
