package com.github.matkovd.throttler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
@AllArgsConstructor
public class ThrottleState {
    AtomicLong passed;
    AtomicLong total;
}
