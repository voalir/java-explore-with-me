package ru.practicum.controller.publicity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventPublicController {

    @Autowired
    EventService eventService;

    @GetMapping
    List<EventShortDto> getEvents(@RequestParam String text,
                                  @RequestParam List<Long> categories,
                                  @RequestParam Boolean paid,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                  @RequestParam Boolean onlyAvailable,
                                  @RequestParam String sort,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsByFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    EventFullDto getEventById(@PathVariable Long id) {
        /*Обратите внимание:

    событие должно быть опубликовано
    информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
    информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
*/
        return eventService.getEventPublishedById(id);
    }
}
