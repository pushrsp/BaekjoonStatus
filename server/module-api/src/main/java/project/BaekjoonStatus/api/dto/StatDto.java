package project.BaekjoonStatus.api.dto;

import lombok.*;
import project.BaekjoonStatus.shared.domain.solvedhistory.entity.SolvedHistory;
import project.BaekjoonStatus.shared.domain.tag.entity.Tag;

import javax.validation.constraints.NotEmpty;
import java.util.*;

public class StatDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyCountDto {
        @NotEmpty(message = "년도를 입력해주세요.")
        private String year;
    }

    @NoArgsConstructor
    public static class SolvedHistoriesByUserId {
        public boolean hasNext;
        public List<Problem> problems;

        public void addProblems(List<Problem> problems) {
            ArrayList<Problem> p = new ArrayList<>(problems);
            p.sort((p1, p2) -> {
                if(p1.problemLevel > p2.problemLevel)
                    return -1;
                else if(p1.problemLevel < p2.problemLevel)
                    return 1;

                return Long.compare(p1.problemId, p2.problemId);
            });

            this.problems = p;
        }

        private SolvedHistoriesByUserId(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public static SolvedHistoriesByUserId of(List<SolvedHistory> histories, List<Tag> tags, int limit) {
            SolvedHistoriesByUserId ret = new SolvedHistoriesByUserId(histories.size() > limit);
            Map<Long, Problem> map = new HashMap<>();

            int index = 0;
            while (index < histories.size() && index < limit) {
                map.put(histories.get(index).getProblem().getId(), Problem.of(histories.get(index).getProblem()));
                index++;
            }

            for (Tag tag : tags) {
                if(map.containsKey(tag.getProblem().getId()))
                    map.get(tag.getProblem().getId()).addTag(tag.getTagName());
            }

            ret.addProblems(map.values().stream().toList());

            return ret;
        }

        @Getter
        public static class Problem {
            public Long problemId;
            public String title;
            public Integer problemLevel;
            public List<Tag> tags = new ArrayList<>();

            public void addTag(String tag) {
                tags.add(Tag.of(tag));
            }

            private Problem(Long problemId, String title, Integer problemLevel) {
                this.problemId = problemId;
                this.title = title;
                this.problemLevel = problemLevel;
            }

            public static Problem of(project.BaekjoonStatus.shared.domain.problem.entity.Problem problem) {
                return new Problem(problem.getId(), problem.getTitle(), problem.getLevel());
            }

            @AllArgsConstructor
            public static class Tag {
                public String tag;

                public static Tag of(String tag) {
                    return new Tag(tag);
                }
            }
        }
    }
}
