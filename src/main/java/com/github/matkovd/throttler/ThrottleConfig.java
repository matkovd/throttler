package com.github.matkovd.throttler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ThrottleConfig {
    @Bean
    public Map<Method, ThrottleState> throttleStateMap() {
        return new ConcurrentHashMap<>();
    }
}
