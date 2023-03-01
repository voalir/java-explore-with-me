package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void registerHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, String[] urls, Boolean unique);

}
