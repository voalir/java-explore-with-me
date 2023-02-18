package ru.practicum;

import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatClient {

    void registerHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
