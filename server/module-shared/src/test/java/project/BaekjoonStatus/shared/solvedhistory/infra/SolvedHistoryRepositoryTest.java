package project.BaekjoonStatus.shared.solvedhistory.infra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.member.domain.Member;
import project.BaekjoonStatus.shared.member.infra.MemberRepository;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;
import project.BaekjoonStatus.shared.solvedhistory.domain.*;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.infra.TagRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SolvedHistoryRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SolvedHistoryRepository solvedHistoryRepository;

    @AfterEach
    void tearDown() {
        tagRepository.deleteAllInBatch();
        solvedHistoryRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("여러개의 solved_history를 동시에 저장할 수 있다.")
    @Test
    public void can_save_list_of_solved_history() throws Exception {
        //given
        List<String> problemIds = List.of("1000", "2000", "2500", "3000");
        saveProblems(problemIds);

        List<String> tagNames = List.of("dp", "implementation", "math");
        saveTags(problemIds, tagNames);

        Member member = saveMember();

        LocalDateTime now = LocalDateTime.of(2023, 8, 31, 15, 44);
        List<SolvedHistory> solvedHistories = problemIds.stream()
                .map(id -> createSolvedHistory(member, id, 1, now))
                .collect(Collectors.toList());

        //when
        int size = solvedHistoryRepository.saveAll(solvedHistories);

        //then
        assertThat(size).isEqualTo(problemIds.size());
    }

    @DisplayName("solved_problem_count를 date별로 묶어서 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideDatesAndProblemIds")
    public void can_find_solved_problem_count_and_group_by_date(List<LocalDate> dates, List<String> problemIds, String year , int expected) throws Exception {
        //given
        Member member = saveMember();

        saveProblems(problemIds);

        List<SolvedHistory> solvedHistories = new ArrayList<>();
        for (int i = 0; i < problemIds.size(); i++) {
            solvedHistories.add(createSolvedHistory(member, problemIds.get(i), 1, dates.get(i).atStartOfDay()));
        }

        solvedHistoryRepository.saveAll(solvedHistories);

        //when
        List<CountByDate> countByDate = solvedHistoryRepository.findSolvedProblemCountByDate(member.getId(), year);

        //then
        assertThat(countByDate).hasSize(expected);
    }

    @DisplayName("solved_problem_count를 tier별로 묶어서 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideProblemIdsAndTiers")
    public void can_find_solved_problem_count_and_group_by_tier(List<String> problemIds, List<String> tiers, Map<String, Integer> expected) throws Exception {
        //given
        Member member = saveMember();

        saveProblems(problemIds, tiers);

        List<SolvedHistory> solvedHistories = new ArrayList<>();
        for (int i = 0; i < problemIds.size(); i++) {
            solvedHistories.add(createSolvedHistory(member, problemIds.get(i), tierConverter(tiers.get(i)), LocalDateTime.now()));
        }

        solvedHistoryRepository.saveAll(solvedHistories);

        //when
        List<CountByTier> result = solvedHistoryRepository.findSolvedProblemCountByTier(member.getId());

        //then
        for(CountByTier r: result) {
            assertThat(r.getCount()).isEqualTo((long) expected.get(r.getTier()));
        }
    }

    @DisplayName("solved_problem_count를 tag별로 묶어서 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideProblemIdsAndTags")
    public void can_find_solved_problem_count_and_group_by_tag(List<String> problemIds, List<String> tagNames, Map<String, Integer> expected) throws Exception {
        //given
        Member member = saveMember();

        saveProblems(problemIds);
        saveTags(problemIds, tagNames);

        List<SolvedHistory> solvedHistories = new ArrayList<>();
        for (String problemId : problemIds) {
            solvedHistories.add(createSolvedHistory(member, problemId, 1, LocalDateTime.now()));
        }

        solvedHistoryRepository.saveAll(solvedHistories);

        //when
        List<GroupByTag> result = solvedHistoryRepository.findSolvedProblemCountByTag(member.getId());

        //then
        for(GroupByTag r: result) {
            assertThat(r.getCount()).isEqualTo(expected.get(r.getTag()));
        }
    }

    @DisplayName("member_id를 통해 자신이 푼 문제를 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideLimitAndOffset")
    public void can_find_solved_problem_by_member_id(List<String> problemIds, int limit, int offset, int expected) throws Exception {
        //given
        Member member = saveMember();

        saveProblems(problemIds);

        List<SolvedHistory> solvedHistories = new ArrayList<>();
        for (String problemId : problemIds) {
            solvedHistories.add(createSolvedHistory(member, problemId, 1, LocalDateTime.now()));
        }

        solvedHistoryRepository.saveAll(solvedHistories);

        //when
        List<SolvedHistoryByMemberId> result = solvedHistoryRepository.findAllByMemberId(member.getId(), offset, limit);

        //then
        assertThat(result).hasSize(expected);
    }

    private static Stream<Arguments> provideLimitAndOffset() {
        return Stream.of(
                Arguments.of(List.of("1000", "2000", "3000", "4000"), 2, 0, 2),
                Arguments.of(List.of("1000", "2000", "3000", "4000"), 2, 1, 2),
                Arguments.of(List.of("1000", "2000", "3000", "4000"), 2, 3, 1),
                Arguments.of(List.of("1000", "2000", "3000", "4000"), 2, 4, 0)
        );
    }

    private static Stream<Arguments> provideProblemIdsAndTags() {
        return Stream.of(
                Arguments.of(List.of("1000", "2000"), List.of("dp", "graphs"), Map.of("dp", 2, "graphs", 2)),
                Arguments.of(List.of("1000", "2000", "3000", "4000"), List.of("graphs", "greedy", "data_structures"), Map.of("graphs", 4, "greedy", 4, "data_structures", 4))
        );
    }

    private static Stream<Arguments> provideProblemIdsAndTiers() {
        return Stream.of(
                Arguments.of(List.of("1000", "2000"), List.of("bronze", "silver"), Map.of("bronze", 1, "silver", 1)),
                Arguments.of(List.of("1000", "2000", "3000", "4000"), List.of("bronze", "silver", "gold", "gold"), Map.of("bronze", 1, "silver", 1, "gold", 2))
        );
    }

    private static Stream<Arguments> provideDatesAndProblemIds() {
        return Stream.of(
                Arguments.of(List.of(LocalDate.of(2022, 8, 8), LocalDate.of(2023, 8, 8)), List.of("1000", "2000"), "2023", 1),
                Arguments.of(List.of(LocalDate.of(2022, 8, 8),
                        LocalDate.of(2023, 8, 8)),
                        List.of("1000", "2000"), "2022", 1),
                Arguments.of(List.of(
                            LocalDate.of(2022, 8, 8),
                            LocalDate.of(2022, 12, 31),
                            LocalDate.of(2023, 8, 8),
                            LocalDate.of(2023, 1, 1),
                            LocalDate.of(2023, 12, 31)
                        ),
                        List.of("1000", "2000", "10000", "1202", "5500"), "2023", 3)
        );
    }

    private SolvedHistory createSolvedHistory(Member member, String problemId, Integer level, LocalDateTime now) {
        return SolvedHistory.builder()
                .isBefore(false)
                .createdTime(now)
                .createdDate(now.toLocalDate())
                .member(member)
                .problem(Problem.builder().id(problemId).build())
                .problemLevel(level)
                .build();
    }

    private void saveTags(List<String> problemIds, List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String problemId : problemIds) {
            List<Tag> t = tagNames.stream()
                            .map(tagName -> createTag(tagName, problemId))
                            .collect(Collectors.toList());

            tags.addAll(t);
        }

        tagRepository.saveAll(tags);
    }

    private Tag createTag(String tagName, String problemId) {
        return Tag.builder()
                .problem(Problem.builder().id(problemId).build())
                .tagName(tagName)
                .build();
    }

    private void saveProblems(List<String> problemIds) {
        LocalDateTime now = LocalDateTime.now();
        List<Problem> problems = problemIds.stream()
                .map(id -> createProblem(id, 1, now))
                .collect(Collectors.toList());

        problemRepository.saveAll(problems);
    }

    private void saveProblems(List<String> problemIds, List<String> tiers) {
        LocalDateTime now = LocalDateTime.now();
        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < problemIds.size(); i++) {
            problems.add(createProblem(problemIds.get(i), tierConverter(tiers.get(i)),  now));
        }

        problemRepository.saveAll(problems);
    }

    private Integer tierConverter(String tier) {
        switch (tier) {
            case "bronze":
                return 1;
            case "silver":
                return 6;
            case "gold":
                return 11;
            case "platinum":
                return 16;
            case "diamond":
                return 21;
            case "ruby":
                return 26;
            default:
                return 0;
        }
    }

    private Problem createProblem(String problemId, Integer level, LocalDateTime now) {
        return Problem.builder()
                .id(problemId)
                .level(level)
                .title("title")
                .createdTime(now)
                .build();
    }

    private Member saveMember() {
        return memberRepository.save(createMember(LocalDateTime.now()));
    }

    private Member createMember(LocalDateTime now) {
        return Member.builder()
                .username("username")
                .password("password")
                .baekjoonUsername("baekjoon")
                .isPrivate(true)
                .createdTime(now)
                .modifiedTime(now)
                .build();
    }
}
