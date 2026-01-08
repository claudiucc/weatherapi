package com.caching.proxy.weatherapi.service;

import com.caching.proxy.weatherapi.cache.WeatherCache;
import com.caching.proxy.weatherapi.client.NominatimClient;
import com.caching.proxy.weatherapi.client.OpenMeteoClient;
import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import com.caching.proxy.weatherapi.model.WeatherDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherCache cache;
    private final NominatimClient geoClient;
    private final OpenMeteoClient meteoClient;

    public WeatherDto getWeather(String city) {
        WeatherDto cached = cache.get(city);
        if (Objects.nonNull(cached)) {
            return cached;
        }

        NominatimPlaceDto location = geoClient.getCoordinates(city);
        WeatherDto weather =
            meteoClient.getWeather(location.getLat(), location.getLon());

        cache.put(city, weather);
        return weather;
    }
}

