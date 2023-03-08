package ru.practicum.controller.publicity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CompilationDto;
import ru.practicum.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
public class CompilationPublicController {

    @Autowired
    CompilationService compilationService;

    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@RequestParam Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
