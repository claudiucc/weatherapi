package com.caching.proxy.weatherapi.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class NominatimClientIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private NominatimClient nominatimClient;

    @Test
    void shouldReturnCoordinatesUsingSpringContext() {
        NominatimPlaceDto dto = new NominatimPlaceDto();
        String city = "Paris";
        String url = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json";

        Mockito.when(restTemplate.exchange(
            Mockito.eq(url),
            Mockito.eq(HttpMethod.GET),
            Mockito.isNull(),
            Mockito.<ParameterizedTypeReference<List<NominatimPlaceDto>>>any()
        )).thenReturn(ResponseEntity.ok(List.of(dto)));

        NominatimPlaceDto result = nominatimClient.getCoordinates(city);

        assertThat(result).isEqualTo(dto);
    }
}

