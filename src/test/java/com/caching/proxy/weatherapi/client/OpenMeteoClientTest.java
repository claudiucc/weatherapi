package com.caching.proxy.weatherapi.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class OpenMeteoClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenMeteoClient openMeteoClient;

    @Test
    void shouldReturnWeatherWhenApiReturnsData() {
        WeatherDto weatherDto = new WeatherDto();
        String lat = "52.52";
        String lon = "13.41";

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

