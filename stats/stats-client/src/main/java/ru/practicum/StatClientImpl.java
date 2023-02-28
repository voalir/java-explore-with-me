package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatClientImpl implements StatClient {

    @Value("${stats-client.url}")
    private String url;
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {
        restTemplate.postForObject(url, endpointHitDto, EndpointHitDto.class);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ViewStatsDto>>() {
        }, start, end, uris, unique).getBody();
    }
}
