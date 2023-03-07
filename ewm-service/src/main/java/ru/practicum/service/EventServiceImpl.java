package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<EventFullDto.StateEnum> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public NewEventDto addEvent(Long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsByFilter(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventFullDto getEventPublishedById(Long id) {
        return null;
    }
}
