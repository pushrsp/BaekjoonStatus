package project.BaekjoonStatus.shared.member.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class MemberEntityTest {
    @DisplayName("member 도메인을 통해 member 엔티티를 만들 수 있다.")
    @Test
    public void can_create_member_entity_by_member_domain() throws Exception {
        //given
        Member memberDomain = createMemberDomain("test", "test", getDateService());

        //when
        MemberEntity memberEntity = MemberEntity.from(memberDomain);

        //then
        assertThat(memberEntity.getId()).isNull();
        assertThat(memberEntity.getUsername()).isEqualTo(memberDomain.getUsername());
        assertThat(memberEntity.getPassword()).isEqualTo(memberDomain.getPassword());
        assertThat(memberEntity.getBaekjoonUsername()).isEqualTo(memberDomain.getBaekjoonUsername());
        assertThat(memberEntity.getIsPrivate()).isEqualTo(memberDomain.getIsPrivate());
        assertThat(memberEntity.getCreatedTime()).isEqualTo(memberDomain.getCreatedTime());
        assertThat(memberEntity.getModifiedTime()).isEqualTo(memberDomain.getModifiedTime());
    }

    @Test
    public void can_convert_to_member_domain_from_member_entity() throws Exception {
        //given
        MemberEntity memberEntity = createMemberEntity(1L, "test", "test", getDateService());

        //when
        Member memberDomain = memberEntity.to();

        //then
        assertThat(memberDomain.getId()).isEqualTo(String.valueOf(memberEntity.getId()));
        assertThat(memberDomain.getUsername()).isEqualTo(memberEntity.getUsername());
        assertThat(memberDomain.getPassword()).isEqualTo(memberEntity.getPassword());
        assertThat(memberDomain.getBaekjoonUsername()).isEqualTo(memberEntity.getBaekjoonUsername());
        assertThat(memberDomain.getIsPrivate()).isEqualTo(memberEntity.getIsPrivate());
        assertThat(memberDomain.getCreatedTime()).isEqualTo(memberEntity.getCreatedTime());
        assertThat(memberDomain.getModifiedTime()).isEqualTo(memberEntity.getModifiedTime());
    }

    private Member createMemberDomain(String username, String password, DateService dateService) {
        return Member.builder()
                .username(username)
                .password(password)
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(dateService.getDateTime())
                .modifiedTime(dateService.getDateTime())
                .build();
    }

    private MemberEntity createMemberEntity(Long id, String username, String password, DateService dateService) {
        return MemberEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(dateService.getDateTime())
                .modifiedTime(dateService.getDateTime())
                .build();
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
                return LocalDateTime.of(2023, 8, 16, 16, 44);
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
