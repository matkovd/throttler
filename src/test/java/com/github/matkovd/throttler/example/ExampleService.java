package com.github.matkovd.throttler.example;

import com.github.matkovd.throttler.Throttle;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    @Throttle(10)
    public Object example() {
        return "pass";
    }
}
