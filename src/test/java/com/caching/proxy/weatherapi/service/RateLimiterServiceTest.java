package com.caching.proxy.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.caching.proxy.weatherapi.exception.RateLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateLimiterServiceTest {

    private RateLimiterService rateLimiterService;

    @BeforeEach
    void setUp() {
        rateLimiterService = new RateLimiterService();
    }

    @Test
    void shouldAllowUpTo5RequestsPerUser() {
        String userId = "user1";

        // Calls 1 to 5 should succeed
        for (int i = 1; i <= 5; i++) {
            rateLimiterService.validate(userId);
        }

        // 6th call should throw exception
        assertThatThrownBy(() -> rateLimiterService.validate(userId))
            .isInstanceOf(RateLimitExceededException.class)
            .hasMessageContaining("Rate limit exceeded");
    }

    @Test
    void shouldResetCounters() {
        String userId = "user2";

        // Increment counter
        rateLimiterService.validate(userId);
        rateLimiterService.validate(userId);

        // Call reset
        rateLimiterService.resetCounters();

        // Should start counting from zero again
        for (int i = 1; i <= 5; i++) {
            rateLimiterService.validate(userId);
        }

        assertThatThrownBy(() -> rateLimiterService.validate(userId))
            .isInstanceOf(RateLimitExceededException.class);
    }

    @Test
    void shouldSeparateCountersPerUser() {
        String userA = "A";
        String userB = "B";

        // Increment user A 5 times
        for (int i = 0; i < 5; i++) rateLimiterService.validate(userA);

        // User B should still have 0 count
        for (int i = 0; i < 5; i++) rateLimiterService.validate(userB);

        // 6th call for user A should throw
        assertThatThrownBy(() -> rateLimiterService.validate(userA))
            .isInstanceOf(RateLimitExceededException.class);

        // 6th call for user B should throw
        assertThatThrownBy(() -> rateLimiterService.validate(userB))
            .isInstanceOf(RateLimitExceededException.class);
    }
}

