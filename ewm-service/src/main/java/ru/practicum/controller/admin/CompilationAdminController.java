package ru.practicum.controller.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;

import javax.validation.Valid;

@RestController("/admin/compilations")
public class CompilationAdminController {

    @PostMapping
    CompilationDto addCompilationDto(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        throw new RuntimeException("not implemented");
    }

    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable Long compId) {
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{compId}")
    CompilationDto updateCompilation(@PathVariable Long compId,
                                     @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        throw new RuntimeException("not implemented");
    }
}
