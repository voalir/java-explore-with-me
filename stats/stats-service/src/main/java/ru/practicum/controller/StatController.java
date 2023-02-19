package ru.practicum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.ViewStatsDto;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatController {

    @Autowired
    StatService statService;

    @GetMapping("/stats")
    List<ViewStatsDto> getViewStats(@RequestParam LocalDateTime start,
                                    @RequestParam LocalDateTime end,
                                    @RequestParam(required = false) String[] uris,
                                    @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        return statService.getViewStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    void registerHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        statService.registerHit(endpointHitDto);
    }
}
