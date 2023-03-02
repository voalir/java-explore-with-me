package ru.practicum.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompilationDto compilationDto = (CompilationDto) o;
        return Objects.equals(this.events, compilationDto.events) &&
                Objects.equals(this.id, compilationDto.id) &&
                Objects.equals(this.pinned, compilationDto.pinned) &&
                Objects.equals(this.title, compilationDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, id, pinned, title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CompilationDto {\n");

        sb.append("    events: ").append(toIndentedString(events)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    pinned: ").append(toIndentedString(pinned)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
