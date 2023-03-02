package ru.practicum.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewCompilationDto newCompilationDto = (NewCompilationDto) o;
        return Objects.equals(this.events, newCompilationDto.events) &&
                Objects.equals(this.pinned, newCompilationDto.pinned) &&
                Objects.equals(this.title, newCompilationDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, pinned, title);
    }

    @Override
    public String toString() {
        return "class NewCompilationDto {\n" +
                "    events: " + toIndentedString(events) + "\n" +
                "    pinned: " + toIndentedString(pinned) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
