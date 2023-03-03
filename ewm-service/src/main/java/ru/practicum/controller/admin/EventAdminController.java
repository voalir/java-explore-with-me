package ru.practicum.controller.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.UpdateEventAdminRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
public class EventAdminController {

    @GetMapping
    List<EventFullDto> getEvents(@RequestParam List<Long> users,
                                 @RequestParam List<String> states,
                                 @RequestParam List<Long> categories,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{eventId}")
    EventFullDto updateEvent(@PathVariable Long eventId,
                             @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
/*Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:

    дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
    событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
    событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
*/
        throw new RuntimeException("not implemented");
    }
}
