package com.github.matkovd.throttler.example;

import com.github.matkovd.throttler.Throttle;
import org.springframework.stereotype.Service;

import static com.github.matkovd.throttler.example.Constant.PASS;
import static com.github.matkovd.throttler.example.Constant.PERCENTAGE;

@Service
public class ExampleService {
    @Throttle(PERCENTAGE)
    public Object example() {
        return PASS;
    }
}
