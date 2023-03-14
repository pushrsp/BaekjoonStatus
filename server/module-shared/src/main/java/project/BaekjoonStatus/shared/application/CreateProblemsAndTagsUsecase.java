package project.BaekjoonStatus.shared.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.BaekjoonStatus.shared.domain.problem.entity.Problem;
import project.BaekjoonStatus.shared.domain.problem.service.ProblemWriteService;
import project.BaekjoonStatus.shared.domain.problemtag.service.ProblemTagWriteService;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;
import project.BaekjoonStatus.shared.domain.tag.service.TagWriteService;
import project.BaekjoonStatus.shared.dto.command.CreateProblemsAndTagsCommand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateProblemsAndTagsUsecase {
    private final ProblemWriteService problemWriteService;
    private final ProblemTagWriteService problemTagWriteService;
    private final TagWriteService tagWriteService;

    @Transactional
    public int execute(CreateProblemsAndTagsCommand command) {
        List<Tag> tags = tagWriteService.bulkInsertByCommandTags(command.getTags());
        List<Problem> problems = problemWriteService.bulkInsertByCommandInfos(command.getProblemInfos());

        problemTagWriteService.bulkInsertByProblemInfos(command.getProblemInfos(), problems, tags);

        return problems.size();
    }
}
