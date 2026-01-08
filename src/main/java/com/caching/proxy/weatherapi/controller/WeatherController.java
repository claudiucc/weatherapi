package com.caching.proxy.weatherapi.controller;

import com.caching.proxy.weatherapi.model.WeatherDto;
import com.caching.proxy.weatherapi.ratelimiter.RateLimited;
import com.caching.proxy.weatherapi.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    @RateLimited
    public ResponseEntity<WeatherDto> getWeather(@RequestParam String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }
}

