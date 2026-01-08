package com.caching.proxy.weatherapi.cache;

import com.caching.proxy.weatherapi.model.WeatherDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class WeatherCache {

    private final Cache<String, WeatherDto> cache =
        Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public WeatherDto get(String city) {
        return cache.getIfPresent(city.toLowerCase());
    }

    public void put(String city, WeatherDto response) {
        cache.put(city.toLowerCase(), response);
    }
}

