package ru.practicum.controller.publicity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CompilationDto;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
public class CompilationPublicController {

    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/{compId}")
    CompilationDto getCompilationById(@RequestParam Integer compId) {
        throw new RuntimeException("not implemented");
    }
}
