package com.caching.proxy.weatherapi.aspect;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.caching.proxy.weatherapi.exception.ServiceBadRequestException;
import com.caching.proxy.weatherapi.ratelimiter.RateLimiterAspect;
import com.caching.proxy.weatherapi.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RateLimiterAspectTest {

    @Mock
    private RateLimiterService rateLimiterService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RateLimiterAspect rateLimiterAspect;

    @Test
    void shouldThrowExceptionWhenUserIdHeaderIsMissing() {
        when(request.getHeader("USER_ID")).thenReturn(null);

        assertThatThrownBy(() -> rateLimiterAspect.validateRateLimit())
            .isInstanceOf(ServiceBadRequestException.class)
            .hasMessageContaining("USER_ID header is required");

        verifyNoInteractions(rateLimiterService);
    }

    @Test
    void shouldThrowExceptionWhenUserIdHeaderIsBlank() {
        when(request.getHeader("USER_ID")).thenReturn(" ");

        assertThatThrownBy(() -> rateLimiterAspect.validateRateLimit())
            .isInstanceOf(ServiceBadRequestException.class);

        verifyNoInteractions(rateLimiterService);
    }

    @Test
    void shouldCallRateLimiterServiceWhenUserIdIsPresent() {
        when(request.getHeader("USER_ID")).thenReturn("user-123");

        rateLimiterAspect.validateRateLimit();

        verify(rateLimiterService).validate("user-123");
    }
}

