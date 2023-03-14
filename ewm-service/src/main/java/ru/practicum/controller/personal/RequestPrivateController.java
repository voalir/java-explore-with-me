package ru.practicum.controller.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class RequestPrivateController {


    private final RequestService requestService;

    @Autowired
    public RequestPrivateController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/{userId}/requests")
    List<ParticipationRequestDto> getRequestsByUser(@PathVariable Long userId) {
        return requestService.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto createParticipationRequest(@PathVariable Long userId,
                                                       @RequestParam Long eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
