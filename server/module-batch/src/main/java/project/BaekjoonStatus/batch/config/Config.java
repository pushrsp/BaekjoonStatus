package project.BaekjoonStatus.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.shared.util.SolvedAcHttp;

@Configuration
public class Config {
    @Bean
    public SolvedAcHttp solvedAcHttp() {
        return new SolvedAcHttp();
    }
}
