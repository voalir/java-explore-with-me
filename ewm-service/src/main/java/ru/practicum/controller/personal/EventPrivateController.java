package ru.practicum.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.EventService;
import ru.practicum.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Validated
public class EventPrivateController {

    private final EventService eventService;

    private final RequestService requestService;

    @Autowired
    public EventPrivateController(EventService eventService, RequestService requestService) {
        this.eventService = eventService;
        this.requestService = requestService;
    }

    @GetMapping("/{userId}/events")
    List<EventShortDto> getEvents(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsForUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto addEvent(@PathVariable Long userId,
                          @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    EventFullDto getEventById(@PathVariable Long userId,
                              @PathVariable Long eventId,
                              HttpServletRequest request) {
        return eventService.getEventById(userId, eventId, request);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    EventFullDto updateEventById(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @Valid @RequestBody UpdateEventUserRequestDto updateEventUserRequestDto) {
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequestDto);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getParticipationRequests(@PathVariable Long userId,
                                                           @PathVariable Long eventId) {
        return requestService.getParticipationRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResultDto updateStatusRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto) {
        return requestService.updateRequestStatus(userId, eventId, eventRequestStatusUpdateRequestDto);
    }

}
