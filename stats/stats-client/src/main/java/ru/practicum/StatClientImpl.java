package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.ViewStatsDto;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;

public class StatClientImpl implements StatClient {

    @Value("URL")
    String URL;
    RestTemplate restTemplate = new RestTemplate();
    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {
        restTemplate.postForObject(URL, endpointHitDto, EndpointHitDto.class);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        return restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<ViewStatsDto>>() {},start,end,uris,unique).getBody();
    }
}
