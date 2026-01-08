package com.caching.proxy.weatherapi.cache;

import static org.assertj.core.api.Assertions.assertThat;

import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.Test;

class WeatherCacheTest {

    private final WeatherCache weatherCache = new WeatherCache();

    @Test
    void shouldStoreAndRetrieveWeatherByCity() {
        WeatherDto dto = new WeatherDto();

        weatherCache.put("London", dto);
        WeatherDto result = weatherCache.get("London");

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void shouldBeCaseInsensitive() {
        WeatherDto dto = new WeatherDto();

        weatherCache.put("LoNdOn", dto);
        WeatherDto result = weatherCache.get("london");

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void shouldReturnNullWhenNotPresent() {
        WeatherDto result = weatherCache.get("Paris");

        assertThat(result).isNull();
    }

    /*@Test
    void shouldExpireAfterOneMinute() throws InterruptedException {
        WeatherDto dto = new WeatherDto();
        weatherCache.put("Berlin", dto);

        Thread.sleep(61_000);

        assertThat(weatherCache.get("Berlin")).isNull();
    }*/
}

