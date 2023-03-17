package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatClient;
import ru.practicum.dto.*;
import ru.practicum.exception.AccessFailedException;
import ru.practicum.exception.LocationNotFoundException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.*;
import ru.practicum.repository.*;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    public static final String EVENTS_POINT = "/events/";
    private final EventRepository eventRepository;
    private final StatClient statClient;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, StatClient statClient, CategoryRepository categoryRepository, UserRepository userRepository, RequestRepository requestRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.statClient = statClient;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<EventFullDto> getEventsByAdminFilter(List<Long> users, List<EventFullDto.State> states, List<Long> categories,
                                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        List<EventState> eventStates = states == null ? null : states.stream().map(EventMapper::toEventState).collect(Collectors.toList());
        List<Event> events = eventRepository.getEventsByAdminFilter(users, eventStates, categories,
                rangeStart, rangeEnd, from, size);
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = getViews(events);
        return events.stream().map(event -> EventMapper.toEventFullDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        Event event = getEventByIdRaw(eventId);
        validToUpdate(eventId, updateEventAdminRequestDto, event);
        updateEventByRequest(event, updateEventAdminRequestDto);
        Event eventUpdated = eventRepository.save(event);
        return convertEventToFullDto(eventUpdated);
    }

    @Override
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findByInitiator(new User(userId, null, null), pageRequest);
        return convertEventsToShortDto(events);
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId, HttpServletRequest request) {
        User user = getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        if (!event.getInitiator().equals(user)) {
            throw new NotFoundException("event with id=" + eventId + " no found");
        }
        sendStat(request);
        return convertEventToFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsByPublicFilter(String text, List<Long> categories, Boolean paid,
                                                       LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                       Boolean onlyAvailable, String sort, Integer from,
                                                       Integer size, HttpServletRequest request) {
        List<Event> events = eventRepository.getEventsByPublicFilter(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        sendStat(request);
        return getEventShortDtos(onlyAvailable, sort, events);
    }

    @Override
    public EventFullDto getEventPublishedById(Long eventId, HttpServletRequest request) {
        Event event = getEventByIdRaw(eventId);
        sendStat(request);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("event with id=" + eventId + " not published");
        }
        return convertEventToFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsByLocation(
            Long locationId, Integer from, Integer size, String sort, HttpServletRequest request) {
        EventLocation eventLocation = getEventLocationByIdRaw(locationId);
        List<Event> events = eventRepository.getEventsByLocation(eventLocation.getId(), PageRequest.of(from / size, size));
        sendStat(request);
        return getEventShortDtos(false, sort, events);
    }

    @Override
    @Transactional
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        validToAdd(newEventDto);
        User user = getUserByIdRaw(userId);
        Category category = getCategoryByIdRaw(newEventDto.getCategory());
        checkReferEventToLocation(newEventDto);
        return EventMapper.toEventFullDto(
                eventRepository.save(EventMapper.toEvent(newEventDto, user, category)), 0L, 0L);
    }

    private void checkReferEventToLocation(NewEventDto newEventDto) {
        if (!locationRepository.isReferToAnyLocation(
                newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon())) {
            throw new LocationNotFoundException("location not found");
        }
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequestDto updateEventUserRequestDto) {
        Event event = getEventByIdRaw(eventId);
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + eventId + " is too old");
        }
        validToUpdateByUser(event, updateEventUserRequestDto);
        updateEventByRequest(event, updateEventUserRequestDto);
        Event eventUpdated = eventRepository.save(event);
        return convertEventToFullDto(eventUpdated);
    }

    private Event getEventByIdRaw(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("event with id=" + eventId + " not found"));
    }

    private EventLocation getEventLocationByIdRaw(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NotFoundException("location with id=" + locationId + " not found"));
    }

    private EventFullDto convertEventToFullDto(Event event) {
        Long confirmedRequests = (long) getCountConfirmedRequestsByEventId(event.getId());
        Long views = (long) statClient.getStats(
                LocalDateTime.now().minusYears(10), LocalDateTime.now(), new String[]{EVENTS_POINT + event.getId()}, false).size();
        return EventMapper.toEventFullDto(event, confirmedRequests, views);
    }

    private List<EventShortDto> convertEventsToShortDto(List<Event> events) {
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = getViews(events);
        return events.stream().map(event -> EventMapper.toEventShortDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    private void updateEventByRequest(Event event, UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        if (updateEventAdminRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequestDto.getRequestModeration());
        }
        if (updateEventAdminRequestDto.getPaid() != null) {
            event.setPaid(updateEventAdminRequestDto.getPaid());
        }
        if (updateEventAdminRequestDto.getEventDate() != null) {
            event.setEventDate(updateEventAdminRequestDto.getEventDate());
        }
        if (updateEventAdminRequestDto.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequestDto.getAnnotation());
        }
        if (updateEventAdminRequestDto.getCategory() != null) {
            event.setCategory(getCategoryByIdRaw(updateEventAdminRequestDto.getCategory()));
        }
        if (updateEventAdminRequestDto.getDescription() != null) {
            event.setDescription(updateEventAdminRequestDto.getDescription());
        }
        if (updateEventAdminRequestDto.getLocation() != null) {
            event.setLat(updateEventAdminRequestDto.getLocation().getLat());
            event.setLon(updateEventAdminRequestDto.getLocation().getLon());
        }
        if (updateEventAdminRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequestDto.getParticipantLimit());
        }
        if (updateEventAdminRequestDto.getStateAction() != null) {
            event.setState(EventMapper.toEventState(updateEventAdminRequestDto.getStateAction()));
        }
        if (updateEventAdminRequestDto.getTitle() != null) {
            event.setTitle(updateEventAdminRequestDto.getTitle());
        }
    }

    private void updateEventByRequest(Event event, UpdateEventUserRequestDto updateEventUserRequestDto) {
        if (updateEventUserRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequestDto.getRequestModeration());
        }
        if (updateEventUserRequestDto.getPaid() != null) {
            event.setPaid(updateEventUserRequestDto.getPaid());
        }
        if (updateEventUserRequestDto.getEventDate() != null) {
            event.setEventDate(updateEventUserRequestDto.getEventDate());
        }
        if (updateEventUserRequestDto.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequestDto.getAnnotation());
        }
        if (updateEventUserRequestDto.getCategory() != null) {
            event.setCategory(getCategoryByIdRaw(updateEventUserRequestDto.getCategory()));
        }
        if (updateEventUserRequestDto.getDescription() != null) {
            event.setDescription(updateEventUserRequestDto.getDescription());
        }
        if (updateEventUserRequestDto.getLocation() != null) {
            event.setLat(updateEventUserRequestDto.getLocation().getLat());
            event.setLon(updateEventUserRequestDto.getLocation().getLon());
        }
        if (updateEventUserRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit());
        }
        if (updateEventUserRequestDto.getStateAction() != null) {
            event.setState(EventMapper.toEventState(updateEventUserRequestDto.getStateAction()));
        }
        if (updateEventUserRequestDto.getTitle() != null) {
            event.setTitle(updateEventUserRequestDto.getTitle());
        }
    }

    private static void validToUpdateByUser(Event event, UpdateEventUserRequestDto updateEventUserRequestDto) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + event.getId() + " can't update by time start");
        }
        if (event.getState() == EventState.PUBLISHED) {
            throw new AccessFailedException("published event with id=" + event.getId() + "can't be updated");
        }
        if (updateEventUserRequestDto.getEventDate() != null &&
                updateEventUserRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + event.getId() + " can't update by time start");
        }
    }

    private void sendStat(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );
        statClient.registerHit(endpointHitDto);
    }

    private Category getCategoryByIdRaw(Long category) {
        return categoryRepository.findById(category).orElseThrow(
                () -> new NotFoundException("category with id=" + category + " not found"));
    }

    public User getUserByIdRaw(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user with id=" + userId + " not found"));
    }

    private Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events) {
        return requestRepository.findByEvent_IdInAndStatusIs(
                        events, ParticipationRequestStatus.CONFIRMED)
                .stream().collect(Collectors.groupingBy(pr -> pr.getEvent().getId(), Collectors.counting()));
    }

    private Integer getCountConfirmedRequestsByEventId(Long eventId) {
        return requestRepository.countByEventIdIsAndStatusIs(
                eventId, ParticipationRequestStatus.CONFIRMED);
    }

    private Map<Long, Long> getViews(List<Event> events) {
        return statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(), events.stream()
                .map(id -> EVENTS_POINT + id).toArray(String[]::new), false).stream().collect(
                Collectors.toMap(s -> Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
    }

    private List<EventShortDto> getEventShortDtos(Boolean onlyAvailable, String sort, List<Event> events) {
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        if (onlyAvailable != null && onlyAvailable) {
            events = events.stream()
                    .filter(event -> confirmedRequests.getOrDefault(event.getId(), 0L) < (long) event.getParticipantLimit())
                    .collect(Collectors.toList());
        }
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> EVENTS_POINT + e.getId()).toArray(String[]::new), false)
                .stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        if (sort != null && sort.equals("VIEWS")) {
            events = events.stream().sorted(Comparator.comparing(
                    event -> views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
        }

        return events.stream().map(event -> EventMapper.toEventShortDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    private static void validToAdd(NewEventDto newEventDto) {
        if (newEventDto.getEventDate() != null &&
                newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event can't create by time start");
        }
    }

    private static void validToUpdate(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto, Event event) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event with id=" + eventId + " can't update by time start");
        }
        if (updateEventAdminRequestDto.getStateAction() == UpdateEventAdminRequestDto.StateAction.PUBLISH_EVENT &&
                event.getState() != EventState.PENDING) {
            throw new AccessFailedException("Event with id=" + eventId + " can't published");
        }
        if (updateEventAdminRequestDto.getStateAction() == UpdateEventAdminRequestDto.StateAction.REJECT_EVENT &&
                event.getState() == EventState.PUBLISHED) {
            throw new AccessFailedException("Event with id=" + eventId + "can't be rejected");
        }
        if (updateEventAdminRequestDto.getEventDate() != null &&
                updateEventAdminRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event with id=" + eventId + " can't update by time start");
        }
    }
}
