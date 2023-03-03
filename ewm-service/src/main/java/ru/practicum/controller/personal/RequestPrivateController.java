package ru.practicum.controller.personal;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class RequestPrivateController {

    @GetMapping("/{userId}/requests")
    List<ParticipationRequestDto> getRequestsByUser(@PathVariable Long userId) {
        throw new RuntimeException("not implemented");
    }

    @PostMapping("/{userId}/requests")
    ParticipationRequestDto createParticipationRequest(@PathVariable Long userId,
                                                       @RequestParam Long eventId) {
        /*
    нельзя добавить повторный запрос (Ожидается код ошибки 409)
    инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
    нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
    если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
    если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
*/
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId,
                                                       @RequestParam Long requestId) {
        throw new RuntimeException("not implemented");
    }
}
