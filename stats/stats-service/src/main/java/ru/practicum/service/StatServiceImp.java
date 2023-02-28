package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.ViewStatMapper;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                return statRepository.getViewStatsUnique(start, end)
                        .stream().map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
            }
            return statRepository.getViewStatsUniqueByUrls(start, end, List.of(urls))
                    .stream().map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
        }
        if (urls == null) {
            return statRepository.getViewStats(start, end)
                    .stream().map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
        }
        return statRepository.getViewStatsByUrls(start, end, List.of(urls))
                .stream().map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
    }
}
