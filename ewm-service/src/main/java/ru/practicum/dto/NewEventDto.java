package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Новое событие
 */
public final class NewEventDto {

    @Size(min = 20, max = 2000)
    @NotNull
    private final String annotation;
    @NotNull
    private final Long category;
    @Size(min = 20, max = 7000)
    @NotNull
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    @NotNull
    private final LocalDateTime eventDate;
    @NotNull
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    @Size(min = 3, max = 120)
    @NotNull
    private final String title;

    @JsonCreator
    public NewEventDto(String annotation, Long category, String description, LocalDateTime eventDate, Location location,
                       Boolean paid, Integer participantLimit, Boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public Long getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public Location getLocation() {
        return location;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public Boolean getRequestModeration() {
        return requestModeration;
    }

    public String getTitle() {
        return title;
    }

}
