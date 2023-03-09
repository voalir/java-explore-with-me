package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.StatClient;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.service.CompilationService;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    CompilationRepository compilationRepository;
    @Autowired
    EventService eventService;
    @Autowired
    RequestService requestService;
    @Autowired
    StatClient statClient;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventService.getEventsByIdsRaw(newCompilationDto.getEvents());
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(newCompilationDto.getEvents());
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        newCompilationDto.getEvents().stream().map(id -> "/events/" + id).toArray(String[]::new), false).
                stream().collect(
                        Collectors.toMap(s -> Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(
                compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto, events)),
                confirmedRequests, views);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = getCompilationByIdRaw(compId);
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getEvents() != null) {
            compilation.setEvents(eventService.getEventsByIdsRaw(updateCompilationRequest.getEvents()));
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(compilation.getEvents().
                stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        compilation.getEvents().stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false).
                stream().collect(
                        Collectors.toMap(s -> Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation),
                confirmedRequests, views);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> compilations = compilationRepository.findAll(
                Example.of(new Compilation(null, null, pinned, null)), pageRequest).toList();
        List<Event> events = compilations.stream().map(Compilation::getEvents).
                flatMap(List::stream).distinct().collect(Collectors.toList());
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(events.
                stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false).
                stream().collect(
                        Collectors.toMap(s -> Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));
        return compilations.stream().map(compilation ->
                CompilationMapper.toCompilationDto(compilation, confirmedRequests, views)).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = getCompilationByIdRaw(compId);
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(compilation.getEvents().
                stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        compilation.getEvents().stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false).
                stream().collect(
                        Collectors.toMap(s -> Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(compilation, confirmedRequests, views);
    }

    public Compilation getCompilationByIdRaw(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("compilation with id=" + compId + " not found"));
    }
}
