package ru.practicum.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.UpdateEventAdminRequestDto;
import ru.practicum.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
public class EventAdminController {

    private final EventService eventService;

    @Autowired
    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                 @RequestParam(required = false) List<EventFullDto.State> states,
                                 @RequestParam(required = false) List<Long> categories,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsByAdminFilter(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    EventFullDto updateEvent(@PathVariable Long eventId,
                             @RequestBody UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        return eventService.updateEvent(eventId, updateEventAdminRequestDto);
    }
}
