package ru.practicum.dto;

import java.util.List;
import java.util.Objects;

/**
 * Изменение информации о подборке событий. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 */
public final class UpdateCompilationRequest {

    private final List<Long> events;
    private final Boolean pinned;
    private final String title;

    public UpdateCompilationRequest(List<Long> events, Boolean pinned, String title) {
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
        UpdateCompilationRequest updateCompilationRequest = (UpdateCompilationRequest) o;
        return Objects.equals(this.events, updateCompilationRequest.events) &&
                Objects.equals(this.pinned, updateCompilationRequest.pinned) &&
                Objects.equals(this.title, updateCompilationRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, pinned, title);
    }

    @Override
    public String toString() {
        return "class UpdateCompilationRequest {\n" +
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
