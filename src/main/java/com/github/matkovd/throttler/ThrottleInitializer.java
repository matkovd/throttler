package com.github.matkovd.throttler;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class ThrottleInitializer implements InitializingBean {
    private final Map<Method, ThrottleState> throttleStateMap;

    @Autowired
    public ThrottleInitializer(Map<Method, ThrottleState> throttleStateMap) {
        this.throttleStateMap = throttleStateMap;
    }

    @Override
    public void afterPropertiesSet() {
        Set<Method> methods = new Reflections("com.github", new MethodAnnotationsScanner())
                .getMethodsAnnotatedWith(Throttle.class);

        log.trace("Found methods: {}", methods.toString());

        methods.forEach(m ->
                throttleStateMap.put(m, new ThrottleState(
                                new AtomicLong(0), new AtomicLong(0)
                        )
                )
        );

    }
}
