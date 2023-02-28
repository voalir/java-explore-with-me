package ru.practicum.mapper;

import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.ViewStat;

public final class ViewStatMapper {
    public static ViewStatsDto toViewStatsDto(ViewStat viewStat){
        return new ViewStatsDto(viewStat.getApp(), viewStat.getUri(), viewStat.getHits());
    }
}
