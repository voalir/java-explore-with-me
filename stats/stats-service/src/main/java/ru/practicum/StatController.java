package ru.practicum;

import org.springframework.web.bind.annotation.*;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.ViewStatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatController {

    @GetMapping("/stats")
    List<ViewStatsDto> getViewStats(@RequestParam LocalDateTime start,
                                    @RequestParam LocalDateTime end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        throw new RuntimeException("not implemented");
    }

    @PostMapping("/hit")
    void registerHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        throw new RuntimeException("not implemented");
    }
}
