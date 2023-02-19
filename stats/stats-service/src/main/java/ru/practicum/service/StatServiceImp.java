package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.HitMapper;
import ru.practicum.model.ViewStatsDto;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

public class StatServiceImp implements StatService {

    @Autowired
    StatRepository statRepository;

    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {
        statRepository.save(HitMapper.toHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, String[] urls, Boolean unique) {
        return null;
    }
}
