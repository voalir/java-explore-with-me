package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Long compId) {

    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        return null;
    }
}
