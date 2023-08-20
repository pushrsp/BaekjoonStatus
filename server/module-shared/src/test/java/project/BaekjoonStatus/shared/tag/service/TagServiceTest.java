package project.BaekjoonStatus.shared.tag.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.BaekjoonStatus.shared.IntegrationTestSupport;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.problem.infra.ProblemRepository;
import project.BaekjoonStatus.shared.tag.domain.Tag;
import project.BaekjoonStatus.shared.tag.infra.TagRepository;
import project.BaekjoonStatus.shared.tag.service.request.TagCreateSharedServiceRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TagServiceTest extends IntegrationTestSupport {
    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        tagRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("TagCreateSharedServiceRequest를 통해 tag를 저장할 수 있다.")
    @Test
    public void can_save_tag_by_TagCreateSharedServiceRequest() throws Exception {
        //given
        Problem problem = createProblem("1000", "title");
        Problem savedProblem = problemRepository.save(problem);

        TagCreateSharedServiceRequest request = createTagCreateSharedServiceRequest("dp", savedProblem.getId());

        //when
        Tag savedTag = tagService.save(request);

        //then
        assertThat(savedTag.getId()).isNotNull();
        assertThat(savedTag.getTagName()).isEqualTo(request.getTagName());
        assertThat(savedTag.getProblem().getId()).isEqualTo(request.getProblemId());
    }

    @DisplayName("여러개의 TagCreateSharedServiceRequest를 통해 다수의 tag를 동시에 저장할 수 있다.")
    @Test
    public void can_save_tags_by_list_of_TagCreateSharedServiceRequest() throws Exception {
        //given
        Problem p1 = createProblem("1000", "t1");
        Problem p2 = createProblem("1001", "t2");
        Problem p3 = createProblem("1002", "t3");

        int problemSize = problemRepository.saveAll(List.of(p1, p2, p3));

        List<String> tagNames = List.of("math", "graph");
        List<TagCreateSharedServiceRequest> requests = createTagCreateSharedServiceRequests(List.of(p1.getId(), p2.getId(), p3.getId()), tagNames);

        //when
        int tagSize = tagService.saveAll(requests);

        //then
        assertThat(tagSize).isEqualTo(problemSize * tagNames.size());
    }

    @DisplayName("여러개의 problem_id를 통해 다수의 tag를 동시에 찾을 수 있다.")
    @Test
    public void can_find_tags_by_list_of_problem_id() throws Exception {
        //given
        Problem p1 = createProblem("1000", "t1");
        Problem p2 = createProblem("1001", "t2");
        Problem p3 = createProblem("1002", "t3");

        int problemSize = problemRepository.saveAll(List.of(p1, p2, p3));

        List<String> tagNames = List.of("math", "graph");
        List<String> problemIds = List.of(p1.getId(), p2.getId(), p3.getId());
        List<TagCreateSharedServiceRequest> requests = createTagCreateSharedServiceRequests(problemIds, tagNames);

        tagService.saveAll(requests);

        //when
        List<Tag> tags = tagService.findAllByProblemIdsIn(problemIds);

        //then
        assertThat(tags).hasSize(problemSize * tagNames.size());
    }

    private List<TagCreateSharedServiceRequest> createTagCreateSharedServiceRequests(List<String> problemIds, List<String> tagNames) {
        List<TagCreateSharedServiceRequest> requests = new ArrayList<>();
        for (String problemId : problemIds) {
            for(String tagName: tagNames) {
                requests.add(createTagCreateSharedServiceRequest(tagName, problemId));
            }
        }

        return requests;
    }

    private TagCreateSharedServiceRequest createTagCreateSharedServiceRequest(String tagName, String problemId) {
        return TagCreateSharedServiceRequest.builder()
                .tagName(tagName)
                .problemId(problemId)
                .build();
    }

    private Problem createProblem(String id, String title) {
        return Problem.builder()
                .id(id)
                .title(title)
                .level(1)
                .createdTime(LocalDateTime.now())
                .build();
    }
}
