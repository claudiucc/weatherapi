package com.caching.proxy.weatherapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.caching.proxy.weatherapi.cache.WeatherCache;
import com.caching.proxy.weatherapi.client.NominatimClient;
import com.caching.proxy.weatherapi.client.OpenMeteoClient;
import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class WeatherServiceTest {

    @Mock
    private WeatherCache cache;

    @Mock
    private NominatimClient geoClient;

    @Mock
    private OpenMeteoClient meteoClient;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCachedWeatherIfPresent() {
        String city = "Berlin";
        WeatherDto cachedWeather = new WeatherDto();
        when(cache.get(city)).thenReturn(cachedWeather);

        WeatherDto result = weatherService.getWeather(city);

        assertThat(result).isEqualTo(cachedWeather);

        verifyNoInteractions(geoClient, meteoClient); // Cached → no external calls
    }

    @Test
    void shouldFetchWeatherAndCacheWhenNotPresent() {
        String city = "Paris";

        WeatherDto freshWeather = new WeatherDto();
        NominatimPlaceDto location = new NominatimPlaceDto();
        location.setLat("48.8566");
        location.setLon("2.3522");

        when(cache.get(city)).thenReturn(null);
        when(geoClient.getCoordinates(city)).thenReturn(location);
        when(meteoClient.getWeather(location.getLat(), location.getLon()))
            .thenReturn(freshWeather);

        WeatherDto result = weatherService.getWeather(city);

        assertThat(result).isEqualTo(freshWeather);

        verify(cache).put(city, freshWeather); // Ensure caching
    }

    @Test
    void shouldCallGeoAndMeteoClientsInOrder() {
        String city = "London";

        WeatherDto weather = new WeatherDto();
        NominatimPlaceDto location = new NominatimPlaceDto();
        location.setLat("51.5074");
        location.setLon("-0.1278");

        when(cache.get(city)).thenReturn(null);
        when(geoClient.getCoordinates(city)).thenReturn(location);
        when(meteoClient.getWeather(location.getLat(), location.getLon())).thenReturn(weather);

        weatherService.getWeather(city);

        verify(cache).get(city);
        verify(geoClient).getCoordinates(city);
        verify(meteoClient).getWeather(location.getLat(), location.getLon());
        verify(cache).put(city, weather);
    }
}

