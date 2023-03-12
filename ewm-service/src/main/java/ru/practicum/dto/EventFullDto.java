package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * EventFullDto
 */
public final class EventFullDto {

    @NotNull
    private final String annotation;
    @NotNull
    private final CategoryDto category;
    private final Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime createdOn;
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    @NotNull
    private final LocalDateTime eventDate;
    private final Long id;
    @NotNull
    private final UserShortDto initiator;
    @NotNull
    private final LocationDto location;
    @NotNull
    private final Boolean paid;
    private final Integer participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime publishedOn;
    private final Boolean requestModeration;
    private final StateEnum state;
    @NotNull
    private final String title;
    private final Long views;

    @JsonCreator
    public EventFullDto(String annotation, CategoryDto category, Long confirmedRequests, LocalDateTime createdOn,
                        String description, LocalDateTime eventDate, Long id, UserShortDto initiator, LocationDto locationDto,
                        Boolean paid, Integer participantLimit, LocalDateTime publishedOn, Boolean requestModeration,
                        StateEnum state, String title, Long views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.location = locationDto;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getDescription() {
        return description;
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

    public LocationDto getLocation() {
        return location;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public Boolean getRequestModeration() {
        return requestModeration;
    }

    public StateEnum getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public Long getViews() {
        return views;
    }

    /**
     * Список состояний жизненного цикла события
     */
    public enum StateEnum {
        PENDING,

        PUBLISHED,

        CANCELED;

    }
}
