package ru.practicum.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class EventPrivateController {

    @Autowired
    EventService eventService;
    @Autowired
    RequestService requestService;

    @GetMapping("/{userId}/events")
    List<EventShortDto> getEvents(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsForUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    EventFullDto addEvent(@PathVariable Long userId,
                          @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    EventFullDto getEventById(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    EventFullDto updateEventById(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getParticipationRequests(@PathVariable Long userId,
                                                           @PathVariable Long eventId) {
        return requestService.getParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult updateStatusRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return requestService.updateRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }

}
