package ru.practicum.dto;

import java.util.List;

/**
 * Изменение информации о подборке событий. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 */
public final class UpdateCompilationRequestDto {

    private final List<Long> events;
    private final Boolean pinned;
    private final String title;

    public UpdateCompilationRequestDto(List<Long> events, Boolean pinned, String title) {
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
