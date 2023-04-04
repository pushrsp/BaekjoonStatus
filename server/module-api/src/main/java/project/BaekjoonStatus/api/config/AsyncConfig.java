package project.BaekjoonStatus.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(15);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("MyExecutor");
        taskExecutor.initialize();

        return taskExecutor;
    }
}
