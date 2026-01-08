package com.caching.proxy.weatherapi.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.caching.proxy.weatherapi.exception.ServiceInternalServerErrorException;
import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class NominatimClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NominatimClient nominatimClient;

    @Test
    void shouldReturnCoordinatesWhenResponseIsNotEmpty() {
        NominatimPlaceDto dto = new NominatimPlaceDto();
        String city = "Berlin";
        String url = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json";

        when(restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<NominatimPlaceDto>>() {}
        )).thenReturn(ResponseEntity.ok(List.of(dto)));

        NominatimPlaceDto result = nominatimClient.getCoordinates(city);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void shouldThrowExceptionWhenResponseIsEmpty() {
        String city = "UnknownCity";
        String url = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json";

        when(restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<NominatimPlaceDto>>() {}
        )).thenReturn(ResponseEntity.ok(List.of()));

        assertThatThrownBy(() -> nominatimClient.getCoordinates(city))
            .isInstanceOf(ServiceInternalServerErrorException.class)
            .hasMessageContaining("internal server error");
    }
}

