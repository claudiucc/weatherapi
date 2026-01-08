package com.caching.proxy.weatherapi.ratelimiter;

import com.caching.proxy.weatherapi.exception.ServiceBadRequestException;
import com.caching.proxy.weatherapi.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {

    private final RateLimiterService rateLimiterService;
    private final HttpServletRequest request;

    @Before("@annotation(RateLimited)")
    public void validateRateLimit() {
        String userId = request.getHeader("USER_ID");

        if (Objects.isNull(userId) || userId.isBlank()) {
            throw new ServiceBadRequestException("USER_ID header is required");
        }

        rateLimiterService.validate(userId);
    }
}

