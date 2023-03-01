package ru.practicum.mapper;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.Hit;

public final class HitMapper {
    public static Hit toHit(EndpointHitDto endpointHitDto) {
        return new Hit(endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp());
    }
}
