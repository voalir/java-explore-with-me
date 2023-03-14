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

    public static final String EVENTS_POINT = "/events/";
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
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto, events));
        return getCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilationToUpdate = getCompilationByIdRaw(compId);
        if (updateCompilationRequest.getPinned() != null) {
            compilationToUpdate.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getEvents() != null) {
            compilationToUpdate.setEvents(new HashSet<>(getEventsByIdsRaw(updateCompilationRequest.getEvents())));
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilationToUpdate.setTitle(updateCompilationRequest.getTitle());
        }
        Compilation compilation = compilationRepository.save(compilationToUpdate);
        return getCompilationDto(compilation);
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
        Map<Long, Long> views = getViews(compilations.stream().map(Compilation::getEvents)
                .flatMap(Set::stream).collect(Collectors.toSet()));
        return compilations.stream().map(compilation ->
                CompilationMapper.toCompilationDto(compilation, confirmedRequests, views)).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = getCompilationByIdRaw(compId);
        return getCompilationDto(compilation);
    }

    private Compilation getCompilationByIdRaw(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("compilation with id=" + compId + " not found"));
    }

    private List<Event> getEventsByIdsRaw(List<Long> events) {
        return eventRepository.findAllById(events);
    }

    private Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events) {
        return requestRepository.findByEvent_IdInAndStatusIs(
                        events, ParticipationRequestStatus.CONFIRMED)
                .stream().collect(Collectors.groupingBy(pr -> pr.getEvent().getId(), Collectors.counting()));
    }

    private CompilationDto getCompilationDto(Compilation compilation) {
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(compilation.getEvents()
                .stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = getViews(compilation.getEvents());
        return CompilationMapper.toCompilationDto(compilation, confirmedRequests, views);
    }

    private Map<Long, Long> getViews(Set<Event> events) {
        return statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(), events.stream()
                .map(id -> EVENTS_POINT + id).toArray(String[]::new), false).stream().collect(
                Collectors.toMap(s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
    }

}
