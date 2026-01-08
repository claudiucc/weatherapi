/*
package com.caching.proxy.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.caching.proxy.weatherapi.exception.RateLimitExceededException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RateLimiterServiceIT {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Test
    void scheduledResetShouldWork() throws InterruptedException {
        String userId = "springUser";

        // Increment 5 times
        for (int i = 0; i < 5; i++) rateLimiterService.validate(userId);

        // 6th call throws
        assertThatThrownBy(() -> rateLimiterService.validate(userId))
            .isInstanceOf(RateLimitExceededException.class);

        // Wait for 1 minute to trigger scheduled reset
        Thread.sleep(61_000);

        // Should be reset
        for (int i = 0; i < 5; i++) rateLimiterService.validate(userId);
    }
}
*/
