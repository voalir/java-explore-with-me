package ru.practicum.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Подборка событий
 */
public final class NewCompilationDto {

    private final List<Long> events;

    private final Boolean pinned;

    @NotNull
    private final String title;

    public NewCompilationDto(List<Long> events, Boolean pinned, String title) {
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }

    public List<Long> getEvents() {
        return events;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public String getTitle() {
        return title;
    }

}
