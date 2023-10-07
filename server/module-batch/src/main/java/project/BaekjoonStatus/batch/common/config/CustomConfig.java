package project.BaekjoonStatus.batch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.batch.common.service.KrDateService;
import project.BaekjoonStatus.shared.common.service.DateService;

@Configuration
public class CustomConfig {

    @Bean
    public DateService dateService() {
        return new KrDateService();
    }
}
