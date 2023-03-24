package project.BaekjoonStatus.api.dto;

import lombok.*;
import org.springframework.data.domain.Slice;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class StatDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyCountDto {
        @NotEmpty(message = "년도를 입력해주세요.")
        private String year;
    }

    @Data
    @NoArgsConstructor
    public static class SolvedHistoriesByUserId {
        public boolean hasNext;
        public List<Problem> problems = new ArrayList<>();

        public static SolvedHistoriesByUserId of(Slice<SolvedHistory> solvedHistories) {
            SolvedHistoriesByUserId solvedHistoriesByUserId = new SolvedHistoriesByUserId();
            solvedHistoriesByUserId.setHasNext(solvedHistories.hasNext());

            for (SolvedHistory solvedHistory : solvedHistories.getContent())
                solvedHistoriesByUserId.getProblems().add(Problem.of(solvedHistory.getProblem()));

            return solvedHistoriesByUserId;
        }

        @Getter
        private static class Problem {
            public Long problemId;
            public String title;
            public Integer problemLevel;
            public List<Tag> tags = new ArrayList<>();

            private Problem(Long problemId, String title, Integer problemLevel) {
                this.problemId = problemId;
                this.title = title;
                this.problemLevel = problemLevel;
            }

            public static Problem of(project.BaekjoonStatus.shared.domain.problem.entity.Problem problem) {
                Problem problemInfo = new Problem(problem.getId(), problem.getTitle(), problem.getLevel());
                for (project.BaekjoonStatus.shared.domain.tag.entity.Tag tag : problem.getTags())
                    problemInfo.getTags().add(new Tag(tag.getTagName()));

                return problemInfo;
            }

            @AllArgsConstructor
            private static class Tag {
                public String tag;
            }
        }
    }
}
