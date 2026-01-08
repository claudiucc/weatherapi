package com.caching.proxy.weatherapi.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class OpenMeteoClientIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private OpenMeteoClient openMeteoClient;

    @Test
    void shouldReturnWeatherUsingSpringContext() {
        WeatherDto weatherDto = new WeatherDto();
        String lat = "40.71";
        String lon = "-74.01";

        String expectedUrl =
            "https://api.open-meteo.com/v1/forecast" +
                "?latitude=" + lat +
                "&longitude=" + lon +
                "&current_weather=true";

        when(restTemplate.getForObject(expectedUrl, WeatherDto.class))
            .thenReturn(weatherDto);

        WeatherDto result = openMeteoClient.getWeather(lat, lon);

        assertThat(result).isEqualTo(weatherDto);
    }
}

