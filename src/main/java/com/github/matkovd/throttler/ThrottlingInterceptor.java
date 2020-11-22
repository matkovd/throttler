package com.github.matkovd.throttler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Aspect
@Component
public class ThrottlingInterceptor {
    private final Map<Method, ThrottleState> throttleStateMap;

    @Autowired
    public ThrottlingInterceptor(Map<Method, ThrottleState> throttleStateMap) {
        this.throttleStateMap = throttleStateMap;
    }

    @Around("@annotation(com.github.matkovd.throttler.Throttle)")
    public Object throttle(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var throttlingState = throttleStateMap.computeIfAbsent(method, m ->
                new ThrottleState(new AtomicLong(0L), new AtomicLong(0L))
        );

        var maxPercentage = method.getAnnotation(Throttle.class).value();

        var total = throttlingState.getTotal().incrementAndGet();
        var passed = throttlingState.getPassed().get();
        var percentage = ((double) passed / total) * 100;

        if (percentage < maxPercentage) {
            log.trace("Method call passed, percentage before call: {}", percentage);
            throttlingState.getPassed().incrementAndGet();
            return joinPoint.proceed();
        }

        log.trace("Method call skipped, percentage before call: {}", percentage);
        return null;
    }
}
