package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatClient;
import ru.practicum.dto.*;
import ru.practicum.exception.AccessFailedException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final StatClient statClient;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, StatClient statClient, CategoryRepository categoryRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.statClient = statClient;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<EventFullDto.StateEnum> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (users != null && users.size() > 0) {
            predicates.add(eventRoot.get("initiator").in(users));
        }
        if (states != null && states.size() > 0) {
            predicates.add(eventRoot.get("state").in(states.stream().map(EventMapper::toEventState).collect(Collectors.toList())));
        }
        if (categories != null && categories.size() > 0) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd));
        }
        query.select(eventRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(criteriaBuilder.asc(eventRoot.get("eventDate")));
        List<Event> events = entityManager.createQuery(query).setFirstResult(from).setMaxResults(size)
                .getResultList();
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false)
                .stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        return events.stream().map(event -> EventMapper.toEventFullDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = getEventByIdRaw(eventId);
        validToUpdate(eventId, updateEventAdminRequest, event);
        updateEventByRequest(event, updateEventAdminRequest);
        Event eventUpdated = eventRepository.save(event);
        return convertEventToFullDto(eventUpdated);
    }

    @Override
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findByInitiator(new User(userId, null, null), pageRequest);
        return convertEventsToShortDto(events);
    }

    private static void validToUpdate(Long eventId, UpdateEventAdminRequest updateEventAdminRequest, Event event) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event with id=" + eventId + " can't update by time start");
        }
        if (updateEventAdminRequest.getStateAction() == UpdateEventAdminRequest.StateActionEnum.PUBLISH_EVENT &&
                event.getState() != EventState.PENDING) {
            throw new AccessFailedException("Event with id=" + eventId + " can't published");
        }
        if (updateEventAdminRequest.getStateAction() == UpdateEventAdminRequest.StateActionEnum.REJECT_EVENT &&
                event.getState() == EventState.PUBLISHED) {
            throw new AccessFailedException("Event with id=" + eventId + "can't be rejected");
        }
        if (updateEventAdminRequest.getEventDate() != null &&
                updateEventAdminRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event with id=" + eventId + " can't update by time start");
        }
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

    private static void validToAdd(NewEventDto newEventDto) {
        if (newEventDto.getEventDate() != null &&
                newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new AccessFailedException("Event can't create by time start");
        }
    }


    @Override
    public List<EventShortDto> getEventsByFilter(String text, List<Long> categories, Boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                 Boolean onlyAvailable, String sort, Integer from,
                                                 Integer size, HttpServletRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (text != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("title")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("annotation")), "%" + text.toLowerCase() + "%"),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("description")), "%" + text.toLowerCase() + "%")
            ));
        }
        if (categories != null && categories.size() > 0) {
            predicates.add(eventRoot.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd));
        }
        if (paid != null) {
            predicates.add(criteriaBuilder.equal(eventRoot.get("paid"), paid));
        }
        query.select(eventRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(criteriaBuilder.asc(eventRoot.get("eventDate")));
        List<Event> events = entityManager.createQuery(query).setFirstResult(from).setMaxResults(size)
                .getResultList();
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        if (onlyAvailable != null && onlyAvailable) {
            events = events.stream()
                    .filter(event -> confirmedRequests.getOrDefault(event.getId(), 0L) < (long) event.getParticipantLimit())
                    .collect(Collectors.toList());
        }
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false)
                .stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));
        if (sort != null && sort.equals("VIEWS")) {
            events = events.stream().sorted(Comparator.comparing(
                    event -> views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
        }
        sendStat(request);
        return events.stream().map(event -> EventMapper.toEventShortDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
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

    public Event getEventByIdRaw(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("event with id=" + eventId + " not found"));
    }

    private EventFullDto convertEventToFullDto(Event event) {
        Long confirmedRequests = (long) getCountConfirmedRequestsByEventId(event.getId());
        Long views = (long) statClient.getStats(
                LocalDateTime.now().minusYears(10), LocalDateTime.now(), new String[]{"/events/" + event.getId()}, false).size();
        return EventMapper.toEventFullDto(event, confirmedRequests, views);
    }

    private List<EventShortDto> convertEventsToShortDto(List<Event> events) {
        Map<Long, Long> confirmedRequests = getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false)
                .stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(8)), ViewStatsDto::getHits));

        return events.stream().map(event -> EventMapper.toEventShortDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    private void updateEventByRequest(Event event, UpdateEventAdminRequest updateEventAdminRequest) {
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            event.setEventDate(updateEventAdminRequest.getEventDate());
        }
        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getCategory() != null) {
            event.setCategory(getCategoryByIdRaw(updateEventAdminRequest.getCategory()));
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getLocation() != null) {
            event.setLat(updateEventAdminRequest.getLocation().getLat());
            event.setLon(updateEventAdminRequest.getLocation().getLon());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            event.setState(EventMapper.toEventState(updateEventAdminRequest.getStateAction()));
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
    }

    private void updateEventByRequest(Event event, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            event.setEventDate(updateEventUserRequest.getEventDate());
        }
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(getCategoryByIdRaw(updateEventUserRequest.getCategory()));
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLat(updateEventUserRequest.getLocation().getLat());
            event.setLon(updateEventUserRequest.getLocation().getLon());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getStateAction() != null) {
            event.setState(EventMapper.toEventState(updateEventUserRequest.getStateAction()));
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
    }

    private static void validToUpdateByUser(Event event, UpdateEventUserRequest updateEventUserRequest) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + event.getId() + " can't update by time start");
        }
        if (event.getState() == EventState.PUBLISHED) {
            throw new AccessFailedException("published event with id=" + event.getId() + "can't be updated");
        }
        if (updateEventUserRequest.getEventDate() != null &&
                updateEventUserRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + event.getId() + " can't update by time start");
        }
    }

    @Override
    @Transactional
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        validToAdd(newEventDto);
        User user = getUserByIdRaw(userId);
        Category category = getCategoryByIdRaw(newEventDto.getCategory());
        return EventMapper.toEventFullDto(
                eventRepository.save(EventMapper.toEvent(newEventDto, user, category)), 0L, 0L);
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = getEventByIdRaw(eventId);
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new AccessFailedException("Event with id=" + eventId + " is too old");
        }
        validToUpdateByUser(event, updateEventUserRequest);
        updateEventByRequest(event, updateEventUserRequest);
        Event eventUpdated = eventRepository.save(event);
        return convertEventToFullDto(eventUpdated);
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

    public Category getCategoryByIdRaw(Long category) {
        return categoryRepository.findById(category).orElseThrow(
                () -> new NotFoundException("category with id=" + category + " not found"));
    }

    public User getUserByIdRaw(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user with id=" + userId + " not found"));
    }

    public Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events) {
        return requestRepository.findByEvent_IdInAndStatusIs(
                        events, ParticipationRequestStatus.CONFIRMED)
                .stream().collect(Collectors.groupingBy(pr -> pr.getEvent().getId(), Collectors.counting()));
    }

    public Integer getCountConfirmedRequestsByEventId(Long eventId) {
        return requestRepository.countByEvent_IdIsAndStatusIs(
                eventId, ParticipationRequestStatus.CONFIRMED);
    }
}
