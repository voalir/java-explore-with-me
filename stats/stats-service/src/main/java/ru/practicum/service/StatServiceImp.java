package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.HitMapper;
import ru.practicum.model.ViewStatsDto;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatServiceImp implements StatService {

    @Autowired
    StatRepository statRepository;

    @Override
    public void registerHit(EndpointHitDto endpointHitDto) {
        statRepository.save(HitMapper.toHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, String[] urls, Boolean unique) {
        if (unique) {
            if (urls == null) {
                return statRepository.getViewStatsUnique(start, end);
            } else {
                return statRepository.getViewStatsUniqueByUrls(start, end, List.of(urls));
            }
        } else {
            if (urls == null) {
                return statRepository.getViewStats(start, end);
            } else {
                return statRepository.getViewStatsByUrls(start, end, List.of(urls));
            }
        }
    }
}
