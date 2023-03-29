package project.BaekjoonStatus.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long duration = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        log.info("처리 시간: {}ms", duration);
    }
}
