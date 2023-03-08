package ru.practicum.mapper;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(null, events, newCompilationDto.getPinned(), newCompilationDto.getTitle());
    }

    public static CompilationDto toCompilationDto(
            Compilation compilation, Map<Long, Long> confirmedRequests, Map<Long, Long> views) {
        return new CompilationDto(
                compilation.getEvents().stream().map(
                                event -> EventMapper.toEventShortDto(event,
                                        confirmedRequests.getOrDefault(event.getId(), 0L),
                                        views.getOrDefault(event.getId(), 0L)))
                        .collect(Collectors.toList()),
                compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
