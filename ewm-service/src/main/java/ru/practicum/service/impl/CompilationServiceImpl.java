package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatClient;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.ParticipationRequestStatus;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.service.CompilationService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final StatClient statClient;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, StatClient statClient, EventRepository eventRepository, RequestRepository requestRepository) {
        this.compilationRepository = compilationRepository;
        this.statClient = statClient;
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>(getEventsByIdsRaw(newCompilationDto.getEvents()));
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(newCompilationDto.getEvents());
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                newCompilationDto.getEvents().stream()
                        .map(id -> "/events/" + id).toArray(String[]::new), false).stream().collect(
                Collectors.toMap(s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(
                compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto, events)),
                confirmedRequests, views);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = getCompilationByIdRaw(compId);
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getEvents() != null) {
            compilation.setEvents(new HashSet<>(getEventsByIdsRaw(updateCompilationRequest.getEvents())));
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(compilation.getEvents()
                .stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                compilation.getEvents().stream()
                        .map(e -> "/events/" + e.getId()).toArray(String[]::new), false).stream().collect(
                Collectors.toMap(s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation),
                confirmedRequests, views);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> compilations = compilationRepository.findAll(
                Example.of(new Compilation(null, null, pinned, null)), pageRequest).toList();
        List<Event> events = compilations.stream().map(Compilation::getEvents)
                .flatMap(Set::stream).collect(Collectors.toList());
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(events
                .stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false)
                .stream().collect(Collectors.toMap(
                        s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        return compilations.stream().map(compilation ->
                CompilationMapper.toCompilationDto(compilation, confirmedRequests, views)).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = getCompilationByIdRaw(compId);
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(compilation.getEvents()
                .stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        compilation.getEvents().stream()
                                .map(e -> "/events/" + e.getId()).toArray(String[]::new), false).stream()
                .collect(Collectors.toMap(s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        return CompilationMapper.toCompilationDto(compilation, confirmedRequests, views);
    }

    public Compilation getCompilationByIdRaw(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("compilation with id=" + compId + " not found"));
    }

    public List<Event> getEventsByIdsRaw(List<Long> events) {
        return eventRepository.findAllById(events);
    }

    public Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events) {
        return requestRepository.findByEvent_IdInAndStatusIs(
                        events, ParticipationRequestStatus.CONFIRMED)
                .stream().collect(Collectors.groupingBy(pr -> pr.getEvent().getId(), Collectors.counting()));
    }

}
