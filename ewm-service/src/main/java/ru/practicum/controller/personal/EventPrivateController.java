package ru.practicum.controller.personal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class EventPrivateController {

    @GetMapping("/{userId}/events")
    List<EventShortDto> getEvents(@PathVariable Long userId,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        throw new RuntimeException("not implemented");
    }

    @PostMapping("/{userId}/events")
    NewEventDto addEvent(@PathVariable Long userId,
                         @Valid @RequestBody NewEventDto newEventDto) {
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/{userId}/events/{eventId}")
    EventFullDto getEventById(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{userId}/events/{eventId}")
    EventFullDto updateEventById(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    ResponseEntity<Object> getParticipationRequest(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult updateStatusRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        throw new RuntimeException("not implemented");
    }

}
