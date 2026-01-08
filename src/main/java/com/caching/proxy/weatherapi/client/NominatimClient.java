package com.caching.proxy.weatherapi.client;

import com.caching.proxy.weatherapi.exception.ServiceInternalServerErrorException;
import com.caching.proxy.weatherapi.model.NominatimPlaceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NominatimClient {

    private final RestTemplate restTemplate;

    public NominatimPlaceDto getCoordinates(String city) {
        String url = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json";

        ResponseEntity<List<NominatimPlaceDto>> response =
            restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
            );

        return response.getBody()
            .stream()
            .findFirst()
            .orElseThrow(() -> new ServiceInternalServerErrorException("An internal server error occured while getting the coordinates!"));
    }
}

