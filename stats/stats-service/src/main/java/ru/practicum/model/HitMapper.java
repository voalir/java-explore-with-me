package ru.practicum.model;

public final class HitMapper {
    public static Hit toHit(EndpointHitDto endpointHitDto) {
        return new Hit(endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp());
    }
}
