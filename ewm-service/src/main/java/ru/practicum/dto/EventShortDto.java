package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

}
