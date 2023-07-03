package project.BaekjoonStatus.api.stat.service.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import project.BaekjoonStatus.api.stat.service.annotation.RedisCacheable;
import project.BaekjoonStatus.shared.common.utils.DateProvider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheableAspect {
    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(redisCacheable)")
    public Object doRedis(ProceedingJoinPoint joinPoint, RedisCacheable redisCacheable) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String key = getKey(redisCacheable.key(), joinPoint.getArgs(), methodSignature.getParameterNames(), Arrays.stream(redisCacheable.paramNames()).toList());

        Object ret = redisTemplate.opsForValue().get(key);
        if(!Objects.isNull(ret)) {
            return ret;
        }

        Object result = joinPoint.proceed();
        if(Objects.isNull(result)) {
            return new ArrayList<>();
        }

        Duration between = Duration.between(DateProvider.getDateTime(), DateProvider.getNextCacheKey());
        redisTemplate.opsForValue().setIfAbsent(key, result, between.toSeconds(), TimeUnit.SECONDS);

        return result;
    }

    private String getKey(String key, Object[] args, String[] paramNames, List<String> redisParamNames) {
        StringBuilder ret = new StringBuilder(key);
        for (int i = 0; i < paramNames.length; i++) {
            if(redisParamNames.contains(paramNames[i])) {
                ret.append(":").append(args[i]);
            }
        }

        return ret.toString();
    }
}