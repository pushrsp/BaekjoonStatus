package project.BaekjoonStatus.batch.job;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import project.BaekjoonStatus.shared.util.BaekjoonCrawling;

import java.util.ArrayList;
import java.util.List;

public class ProblemItemReader implements ItemReader<Long> {

    private List<Long> problemIds = new ArrayList<>();

    public ProblemItemReader(List<Long> problemIds) {
        this.problemIds = problemIds;
    }

    public ProblemItemReader(String baekjoonUsername) {
        init(baekjoonUsername);
    }

    private void init(String baekjoonUsername) {
        BaekjoonCrawling crawling = new BaekjoonCrawling(baekjoonUsername);
        this.problemIds.addAll(crawling.getMySolvedHistories());
    }

    @Override
    public Long read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(problemIds.isEmpty())
            return null;

        return problemIds.remove(0);
    }
}
