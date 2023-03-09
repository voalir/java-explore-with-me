package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatClientImpl implements StatClient {

    @Value("${stats-client.url}")
    private String url;

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {
        restTemplate.postForObject(url, endpointHitDto, EndpointHitDto.class);
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        Map<String, Object> params = new HashMap<String, Object>();
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url + "/stats")
                .queryParam("start", "{start}")
                .queryParam("end", "{end}")
                .queryParam("uris", "{uris}")
                .queryParam("unique", "{unique}")
                .encode()
                .toUriString();
        params.put("start", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("end", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("uris", Arrays.toString(uris));
        params.put("unique", unique.toString());
        HttpEntity<Object> requestEntity = new HttpEntity<>(defaultHeaders());
        return restTemplate.exchange(urlTemplate, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ViewStatsDto>>() {

        }, params).getBody();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
