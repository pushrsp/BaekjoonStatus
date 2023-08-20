package project.BaekjoonStatus.shared.tag.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TagRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        tagRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("tag를 저장할 수 있다.")
    @Test
    public void can_save_tag() throws Exception {
        //given
        Problem problem = saveProblem("1000");
        Tag tag = createTagDomain(problem, "dp");

        //when
        Tag savedTag = tagRepository.save(tag);

        //then
        assertThat(savedTag.getId()).isNotEmpty();
    }

    @DisplayName("여러개의 tag를 동시에 저장할 수 있다.")
    @Test
    public void can_save_list_of_tags() throws Exception {
        //given
        saveProblems(3);

        List<Problem> problems = problemRepository.findAllByIdsIn(List.of("1", "2", "3"));

        List<String> tagNames = List.of("dp", "math", "impl");
        List<Tag> tagDomains = createTagDomains(problems, tagNames);

        //when
        int size = tagRepository.saveAll(tagDomains);

        //then
        assertThat(size).isEqualTo(problems.size() * tagNames.size());
    }

    @DisplayName("여러개의 tag를 여러개의 problem_id를 통해 동시에 찾을 수 있다.")
    @Test
    public void can_find_list_of_tag_by_list_of_problem_id() throws Exception {
        //given
        saveProblems(5);

        List<Problem> problems = problemRepository.findAllByIdsIn(List.of("1", "2", "3"));

        List<String> tagNames = List.of("dp", "math", "impl");
        List<Tag> tagDomains = createTagDomains(problems, tagNames);

        tagRepository.saveAll(tagDomains);

        List<String> problemIds = problems.stream()
                .map(Problem::getId)
                .collect(Collectors.toList());

        //when
        List<Tag> tags = tagRepository.findAllByProblemIdsIn(problemIds);

        //then
        assertThat(tags).hasSize(problems.size() * tagNames.size());
    }

    private void saveProblems(int size) {
        List<Problem> problems = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            problems.add(createProblemDomain(String.valueOf(i)));
        }

        problemRepository.saveAll(problems);
    }

    private Problem saveProblem(String id) {
        return problemRepository.save(createProblemDomain(id));
    }

    private Problem createProblemDomain(String id) {
        return Problem.builder()
                .id(id)
                .title("title " + id)
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }

    private List<Tag> createTagDomains(List<Problem> problems, List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for(Problem problem: problems) {
            for(String tagName: tagNames) {
                tags.add(createTagDomain(problem, tagName));
            }
        }

        return tags;
    }

    private Tag createTagDomain(Problem problem, String tagName) {
        return Tag.builder()
                .tagName(tagName)
                .problem(problem)
                .build();
    }
}
