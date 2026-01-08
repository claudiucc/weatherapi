package com.caching.proxy.weatherapi.service;

import com.caching.proxy.weatherapi.exception.RateLimitExceededException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 60000)
    public void resetCounters() {
        counters.clear();
    }

    public void validate(String userId) {
        counters.putIfAbsent(userId, new AtomicInteger(0));
        int count = counters.get(userId).incrementAndGet();

        if (count > 5) {
            throw new RateLimitExceededException("Rate limit exceeded");
        }
    }
}

