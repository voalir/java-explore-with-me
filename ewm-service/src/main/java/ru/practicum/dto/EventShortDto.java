package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Краткая информация о событии
 */
public final class EventShortDto {

    @NotNull
    private final String annotation;
    @NotNull
    private final CategoryDto category;
    @NotNull
    private final Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    @NotNull
    private final LocalDateTime eventDate;
    private final Long id;
    @NotNull
    private final UserShortDto initiator;
    @NotNull
    private final Boolean paid;
    @NotNull
    private final String title;
    private final Long views;

    public EventShortDto(String annotation, CategoryDto category, Long confirmedRequests, LocalDateTime eventDate, Long id,
                         UserShortDto initiator, Boolean paid, String title, Long views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
    }

    public String getAnnotation() {
        return annotation;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public Long getConfirmedRequests() {
        return confirmedRequests;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public Long getId() {
        return id;
    }

    public UserShortDto getInitiator() {
        return initiator;
    }

    public Boolean getPaid() {
        return paid;
    }

    public String getTitle() {
        return title;
    }

    public Long getViews() {
        return views;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventShortDto eventShortDto = (EventShortDto) o;
        return Objects.equals(this.annotation, eventShortDto.annotation) &&
                Objects.equals(this.category, eventShortDto.category) &&
                Objects.equals(this.confirmedRequests, eventShortDto.confirmedRequests) &&
                Objects.equals(this.eventDate, eventShortDto.eventDate) &&
                Objects.equals(this.id, eventShortDto.id) &&
                Objects.equals(this.initiator, eventShortDto.initiator) &&
                Objects.equals(this.paid, eventShortDto.paid) &&
                Objects.equals(this.title, eventShortDto.title) &&
                Objects.equals(this.views, eventShortDto.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, confirmedRequests, eventDate, id, initiator, paid, title, views);
    }

    @Override
    public String toString() {
        return "class EventShortDto {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    initiator: " + toIndentedString(initiator) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    views: " + toIndentedString(views) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
