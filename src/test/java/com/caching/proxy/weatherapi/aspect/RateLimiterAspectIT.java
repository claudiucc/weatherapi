package com.caching.proxy.weatherapi.aspect;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.caching.proxy.weatherapi.service.RateLimiterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimiterAspectIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateLimiterService rateLimiterService;

    @Test
    void shouldInvokeAspectWhenAnnotationIsPresent() throws Exception {
        mockMvc.perform(get("/api/v1/weather")
            .param("city", "Rome")
            .header("USER_ID", "user-123"))
            .andExpect(status().isOk());

        verify(rateLimiterService).validate("user-123");
    }

    @Test
    void shouldReturn400WhenUserIdHeaderIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/weather")
            .param("city", "Rome"))
            .andExpect(status().isBadRequest());
    }
}

