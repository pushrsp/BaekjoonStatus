package project.BaekjoonStatus.shared.member.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.common.exception.InvalidIdFormatException;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("member를 저장할 수 있다.")
    @Test
    public void can_save_member() throws Exception {
        //given
        Member memberDomain = createMemberDomain("test1", "test1", getDateService());

        //when
        Member savedMember = memberRepository.save(memberDomain);

        //then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo(memberDomain.getUsername());
        assertThat(savedMember.getPassword()).isEqualTo(memberDomain.getPassword());
        assertThat(savedMember.getCreatedTime()).isEqualTo(memberDomain.getCreatedTime());
        assertThat(savedMember.getModifiedTime()).isEqualTo(memberDomain.getModifiedTime());
    }

    @DisplayName("member_id를 통해 member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_member_id() throws Exception {
        //given
        Member memberDomain = createMemberDomain("findById", "password", getDateService());
        Member savedMember = memberRepository.save(memberDomain);

        //when
        Optional<Member> optionalMember = memberRepository.findById(savedMember.getId());

        //then
        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(optionalMember.get().getId()).isEqualTo(savedMember.getId());
    }

    @DisplayName("username을 통해 member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_username() throws Exception {
        //given
        Member memberDomain = createMemberDomain("findByUsername", "password", getDateService());
        Member savedMember = memberRepository.save(memberDomain);

        //when
        Optional<Member> optionalMember = memberRepository.findByUsername(savedMember.getUsername());

        //then
        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(optionalMember.get().getId()).isEqualTo(savedMember.getId());
    }

    @DisplayName("주어진 member_id보다 큰 member_id를 가진 member를 찾을 수 있다.")
    @Test
    public void can_find_members_who_has_greater_member_id_than_given_member_id() throws Exception {
        //given
        saveMembers(20);

        //when
        List<Member> m1 = memberRepository.findAllGreaterThanMemberId("0", 10);
        List<Member> m2 = memberRepository.findAllGreaterThanMemberId("3000", 10);

        //then
        assertThat(m1).hasSize(10);
        assertThat(m2).hasSize(0);
    }

    @DisplayName("member_id가 유효한지 확인할 수 있다.")
    @Test
    public void can_detect_member_id_is_valid() throws Exception {
        //given

        //when
        InvalidIdFormatException invalidIdFormatException = catchThrowableOfType(() -> memberRepository.findById("fdsafsa"), InvalidIdFormatException.class);

        //then
        assertThat(invalidIdFormatException).isNotNull();
        assertThat(invalidIdFormatException.getMessage()).isEqualTo("id 형식이 올바르지 않습니다.");
    }

    private void saveMembers(int size) {
        for (int i = 0; i < size; i++) {
            Member memberDomain = createMemberDomain("test" + i, "password" + i, getDateService());
            memberRepository.save(memberDomain);
        }
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
                return LocalDateTime.of(2023, 8, 16, 17, 2);
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
