package com.caching.proxy.weatherapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.caching.proxy.weatherapi.config.SecurityConfig;
import com.caching.proxy.weatherapi.model.WeatherDto;
import com.caching.proxy.weatherapi.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
@Import(SecurityConfig.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherForCity() throws Exception {
        WeatherDto dto = new WeatherDto();
        String city = "Berlin";

        when(weatherService.getWeather(city)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/weather")
                .param("city", city)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

