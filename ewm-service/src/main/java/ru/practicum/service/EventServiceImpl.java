package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.StatClient;
import ru.practicum.dto.*;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    RequestService requestService;
    @Autowired
    StatClient statClient;

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<EventFullDto.StateEnum> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findEventsByFilter(users, states.stream().map(Enum::name).collect(Collectors.toList()),
                categories, rangeStart, rangeEnd, pageRequest);
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.MIN, LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false).
                stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));
        return events.stream().map(event -> EventMapper.toEventFullDto(event,
                confirmedRequests.getOrDefault(event.getId(), 0L),
                views.getOrDefault(event.getId(), 0L))).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = getEventByIdRaw(eventId);
        updateEventByRequest(event, updateEventAdminRequest);
        Event eventUpdated = eventRepository.save(event);
        return convertEventToFullDto(eventUpdated);
    }

    @Override
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findByUser(new User(userId, null, null), pageRequest);
        return convertEventsToShortDto(events);
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = userService.getUserByIdRaw(userId);
        Category category = categoryService.getCategoryByIdRaw(newEventDto.getCategory());
        return EventMapper.toEventFullDto(
                eventRepository.save(EventMapper.toEvent(newEventDto, user, category)), 0L, 0L);
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        User user = userService.getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        if (!event.getInitiator().equals(user)) {
            throw new NotFoundException("event with id=" + eventId + " no found");
        }
        return convertEventToFullDto(event);
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = getEventByIdRaw(eventId);
        updateEventByRequest(event, updateEventUserRequest);
        Event eventUpdated = eventRepository.save(event);
        return EventMapper.toEventFullDto(eventUpdated, null, null);//TODO replace null to data
    }

    @Override
    public List<EventShortDto> getEventsByFilter(String text, List<Long> categories, Boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                 Boolean onlyAvailable, String sort, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findEventsByFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                pageRequest);
        return convertEventsToShortDto(events);
    }

    @Override
    public EventFullDto getEventPublishedById(Long eventId) {
        Event event = getEventByIdRaw(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("event with id=" + eventId + " not published");
        }
        return convertEventToFullDto(event);
    }

    @Override
    public Event getEventByIdRaw(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("event with id=" + eventId + " not found"));
    }

    @Override
    public Integer getCountConfirmedRequestsByEvent(Event event) {
        return eventRepository.getCountConfirmedRequestsByEvent(event);
    }

    @Override
    public List<Event> getEventsByIdsRaw(List<Long> events) {
        return eventRepository.findAllById(events);
    }

    private EventFullDto convertEventToFullDto(Event event) {
        Long confirmedRequests = (long) requestService.getCountConfirmedRequestsByEventId(event.getId());
        Long views = (long) statClient.getStats(
                LocalDateTime.MIN, LocalDateTime.now(), new String[]{"/events/" + event.getId()}, false).size();
        return EventMapper.toEventFullDto(event, confirmedRequests, views);
    }

    private List<EventShortDto> convertEventsToShortDto(List<Event> events) {
        Map<Long, Long> confirmedRequests = requestService.getCountConfirmedRequestsByEventIds(
                events.stream().map(Event::getId).collect(Collectors.toList()));
        Map<Long, Long> views = statClient.getStats(LocalDateTime.MIN, LocalDateTime.now(),
                        events.stream().map(e -> "/events/" + e.getId()).toArray(String[]::new), false).
                stream().collect(Collectors.toMap(s ->
                        Long.valueOf(s.getUri().substring(9)), ViewStatsDto::getHits));

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
            event.setCategory(categoryService.getCategoryByIdRaw(updateEventAdminRequest.getCategory()));
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
            event.setState(EventState.valueOf(updateEventAdminRequest.getStateAction().name()));
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
            event.setCategory(categoryService.getCategoryByIdRaw(updateEventUserRequest.getCategory()));
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
            event.setState(EventState.valueOf(updateEventUserRequest.getStateAction().name()));
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
    }

}
