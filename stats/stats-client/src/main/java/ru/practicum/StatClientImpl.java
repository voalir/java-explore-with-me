package ru.practicum;

import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public class StatClientImpl implements StatClient {

    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {

    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        return null;
    }
}
