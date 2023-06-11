package project.BaekjoonStatus.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.common.service.github.DailyProblemCrawling;
import project.BaekjoonStatus.shared.common.service.solvedac.SolvedAcHttp;

@Configuration
public class Config {
    @Bean
    public SolvedAcHttp solvedAcHttp() {
        return new SolvedAcHttp();
    }

    @Bean
    public DailyProblemCrawling dailyProblemCrawling() {
        return new DailyProblemCrawling();
    }
}
