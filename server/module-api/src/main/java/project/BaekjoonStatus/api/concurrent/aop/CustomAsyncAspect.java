package project.BaekjoonStatus.api.concurrent.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import project.BaekjoonStatus.api.concurrent.CustomThreadPoolExecutor;
import project.BaekjoonStatus.api.concurrent.Job;
import project.BaekjoonStatus.api.concurrent.annotation.CustomAsync;

@Aspect
@RequiredArgsConstructor
@Component
public class CustomAsyncAspect {
    private final CustomThreadPoolExecutor customThreadPoolExecutor;

    @Around("@annotation(customAsync)")
    public Object doCustomAsync(ProceedingJoinPoint joinPoint, CustomAsync customAsync) {
        Job job = new Job(customAsync.delay(), customAsync.offset(), joinPoint::proceed);
        job.setMaxTry(customAsync.maxTry());

        customThreadPoolExecutor.addJob(job);
        return null;
    }
}
