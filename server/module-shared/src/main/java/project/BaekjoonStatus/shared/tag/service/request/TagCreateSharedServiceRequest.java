package project.BaekjoonStatus.shared.tag.service.request;

import lombok.Builder;
import lombok.Getter;
import project.BaekjoonStatus.shared.problem.domain.Problem;
import project.BaekjoonStatus.shared.tag.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TagCreateSharedServiceRequest {
    private String tagName;
    private String problemId;

    @Builder
    private TagCreateSharedServiceRequest(String tagName, String problemId) {
        this.tagName = tagName;
        this.problemId = problemId;
    }

    public static TagCreateSharedServiceRequest from(Tag tag) {
        return TagCreateSharedServiceRequest.builder()
                .tagName(tag.getTagName())
                .problemId(tag.getProblem().getId())
                .build();
    }

    public static List<Tag> toDomainList(List<TagCreateSharedServiceRequest> requests) {
        return requests.stream()
                .map(TagCreateSharedServiceRequest::toDomain)
                .collect(Collectors.toList());
    }

    public Tag toDomain() {
        return Tag.builder()
                .tagName(this.tagName)
                .problem(Problem.of(this.problemId))
                .build();
    }
}
