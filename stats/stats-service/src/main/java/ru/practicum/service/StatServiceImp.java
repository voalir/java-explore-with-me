package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.mapper.ViewStatMapper;
import ru.practicum.model.ViewStat;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatServiceImp implements StatService {

    @Autowired
    StatRepository statRepository;

    @Override
    @Transactional
    public void registerHit(EndpointHitDto endpointHitDto) {
        statRepository.save(HitMapper.toHit(endpointHitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, String[] urls, Boolean unique) {
        if (unique) {
            if (urls == null) {
                return statRepository.getViewStatsUnique(start, end)
                        .stream().sorted(Comparator.comparing(ViewStat::getHits).reversed()
                        ).map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
            }
            return statRepository.getViewStatsUniqueByUrls(start, end, List.of(urls))
                    .stream().sorted(Comparator.comparing(ViewStat::getHits).reversed()
                    ).map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
        }
        if (urls == null) {
            return statRepository.getViewStats(start, end)
                    .stream().sorted(Comparator.comparing(ViewStat::getHits).reversed()
                    ).map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
        }
        return statRepository.getViewStatsByUrls(start, end, List.of(urls))
                .stream().sorted(Comparator.comparing(ViewStat::getHits).reversed()
                ).map(ViewStatMapper::toViewStatsDto).collect(Collectors.toList());
    }
}
