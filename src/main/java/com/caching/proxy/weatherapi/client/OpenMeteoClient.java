package com.caching.proxy.weatherapi.client;

import com.caching.proxy.weatherapi.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OpenMeteoClient {

    private final RestTemplate restTemplate;

    public WeatherDto getWeather(String lat, String lon) {
        String url = "https://api.open-meteo.com/v1/forecast" +
            "?latitude=" + lat +
            "&longitude=" + lon +
            "&current_weather=true";

        return restTemplate.getForObject(url, WeatherDto.class);
    }
}

