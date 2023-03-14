package ru.practicum.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Подборка событий
 */
public final class CompilationDto {
    private final List<EventShortDto> events;
    @NotNull
    private final Long id;
    @NotNull
    private final Boolean pinned;
    @NotNull
    private final String title;

    public CompilationDto(List<EventShortDto> events, Long id, Boolean pinned, String title) {
        this.events = events;
        this.id = id;
        this.pinned = pinned;
        this.title = title;
    }

    public List<EventShortDto> getEvents() {
        return events;
    }

    public Long getId() {
        return id;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public String getTitle() {
        return title;
    }

}
