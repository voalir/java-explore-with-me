package ru.practicum.controller.publicity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CompilationDto;

import java.util.List;

@RestController("/compilations")
public class CompilationPublicController {

    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam Boolean pinned,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        throw new RuntimeException("not implemented");
    }

    @GetMapping
    CompilationDto getCompilationById(@RequestParam Integer id) {
        throw new RuntimeException("not implemented");
    }
}
