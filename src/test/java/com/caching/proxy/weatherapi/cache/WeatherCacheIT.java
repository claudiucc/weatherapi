package com.caching.proxy.weatherapi.cache;

import static org.assertj.core.api.Assertions.assertThat;

import com.caching.proxy.weatherapi.model.WeatherDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherCacheIT {

    @Autowired
    private WeatherCache weatherCache;

    @Test
    void contextLoadsAndCacheWorks() {
        WeatherDto dto = new WeatherDto();

        weatherCache.put("Madrid", dto);
        WeatherDto result = weatherCache.get("Madrid");

        assertThat(result).isEqualTo(dto);
    }
}
