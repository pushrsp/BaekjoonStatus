package project.BaekjoonStatus.api.common.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.BaekjoonStatus.api.concurrent.CustomThreadPoolExecutor;

@Configuration
public class AsyncConfig {
    @Bean
    public CustomThreadPoolExecutor customThreadPoolExecutor() {
        return new CustomThreadPoolExecutor(1);
    }
}
