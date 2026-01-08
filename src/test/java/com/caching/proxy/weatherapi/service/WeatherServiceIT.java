package com.caching.proxy.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.caching.proxy.weatherapi.cache.WeatherCache;
import com.caching.proxy.weatherapi.client.NominatimClient;
import com.caching.proxy.weatherapi.client.OpenMeteoClient;
import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class WeatherServiceIT {

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private WeatherCache cache;

    @MockBean
    private NominatimClient geoClient;

    @MockBean
    private OpenMeteoClient meteoClient;

    @Test
    void shouldFetchAndCacheWeather() {
        String city = "Tokyo";

        WeatherDto weather = new WeatherDto();
        NominatimPlaceDto location = new NominatimPlaceDto();
        location.setLat("35.6895");
        location.setLon("139.6917");

        when(cache.get(city)).thenReturn(null);
        when(geoClient.getCoordinates(city)).thenReturn(location);
        when(meteoClient.getWeather(location.getLat(), location.getLon())).thenReturn(weather);

        WeatherDto result = weatherService.getWeather(city);

        assertThat(result).isEqualTo(weather);
    }
}

