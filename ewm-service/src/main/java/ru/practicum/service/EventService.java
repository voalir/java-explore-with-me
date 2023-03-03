package ru.practicum.service;

import ru.practicum.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventFullDto> getEvents(List<Long> users, List<EventFullDto.StateEnum> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size);

    NewEventDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    List<EventShortDto> getEventsByFilter(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto getEventPublishedById(Long id);
}
