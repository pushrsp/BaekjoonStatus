package project.BaekjoonStatus.shared.member.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.common.service.DateService;
import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.infra.MemberRepository;
import project.BaekjoonStatus.shared.member.service.request.MemberCreateSharedServiceRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("MemberCreateSharedServiceRequest를 통해 member를 저장할 수 있다.")
    @Test
    public void can_save_member_by_MemberCreateSharedServiceRequest() throws Exception {
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
        Member savedMember = memberService.save(request);

        //then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo(request.getUsername());
        assertThat(savedMember.getPassword()).isEqualTo(request.getPassword());
        assertThat(savedMember.getBaekjoonUsername()).isEqualTo(request.getBaekjoonUsername());
        assertThat(savedMember.getIsPrivate()).isEqualTo(request.getIsPrivate());
        assertThat(savedMember.getCreatedTime()).isEqualTo(request.getCreatedTime());
        assertThat(savedMember.getModifiedTime()).isEqualTo(request.getModifiedTime());
    }

    @DisplayName("member_id를 통해 member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_member_id() throws Exception {
        //given
        MemberCreateSharedServiceRequest request = createMemberCreateSharedServiceRequest("username", "password", getDateService());
        Member savedMember = memberService.save(request);

        //when
        Optional<Member> optionalMember = memberService.findById(savedMember.getId());

        //then
        assertThat(optionalMember.isPresent()).isTrue();
    }

    @DisplayName("username을 통해 member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_username() throws Exception {
        //given
        MemberCreateSharedServiceRequest request = createMemberCreateSharedServiceRequest("findByUsername", "password", getDateService());
        Member savedMember = memberService.save(request);

        //when
        Optional<Member> optionalMember = memberService.findByUsername(savedMember.getUsername());

        //then
        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(optionalMember.get().getUsername()).isEqualTo(savedMember.getUsername());
    }

    @DisplayName("주어진 member_id보다 큰 member_id를 가진 member를 찾을 수 있다.")
    @Test
    public void can_find_members_who_has_greater_member_id_than_given_member_id() throws Exception {
        //given
        saveMembers(20);

        //when
        List<Member> m1 = memberService.findAllByGreaterThanUserId("0", 20);
        List<Member> m2 = memberService.findAllByGreaterThanUserId("2000", 10);

        //then
        assertThat(m1).hasSize(20);
        assertThat(m2).hasSize(0);
    }

    private void saveMembers(int size) {
        for (int i = 0; i < size; i++) {
            MemberCreateSharedServiceRequest request = createMemberCreateSharedServiceRequest("test" + i, "password" + i, getDateService());
            memberService.save(request);
        }
    }

    private MemberCreateSharedServiceRequest createMemberCreateSharedServiceRequest(String username, String password, DateService dateService) {
        return MemberCreateSharedServiceRequest.builder()
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
                return LocalDateTime.of(2023, 8, 16, 17, 51);
            }

            @Override
            public LocalDateTime getNextCacheKey(LocalDateTime now) {
                return null;
            }
        };
    }
}
