package ru.practicum.service;


import ru.practicum.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventFullDto> getEventsByAdminFilter(List<Long> users, List<EventFullDto.State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto);

    List<EventShortDto> getEventsForUser(Long userId, Integer from, Integer size);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long userId, Long eventId, HttpServletRequest request);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequestDto updateEventUserRequestDto);

    List<EventShortDto> getEventsByPublicFilter(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto getEventPublishedById(Long id, HttpServletRequest request);

    List<EventShortDto> getEventsByLocation(Long locationId, Integer from, Integer size, HttpServletRequest request);
}
