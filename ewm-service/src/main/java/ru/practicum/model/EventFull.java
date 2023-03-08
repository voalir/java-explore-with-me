package ru.practicum.model;

import java.time.LocalDateTime;

public class EventFull {
    private final Long id;
    private final String annotation;
    private final Category category;
    private final Long confirmedRequests;
    private final User initiator;
    private final String description;
    private final LocalDateTime eventDate;
    private final LocalDateTime createdOn;
    private final LocalDateTime publishedOn;
    private final Float lat;//location
    private final Float lon;//location
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final String title;
    private final String state;
    private final Long views;

    public EventFull(Long id, String annotation, Category category, Long confirmedRequests, User initiator,
                     String description, LocalDateTime eventDate, LocalDateTime createdOn, LocalDateTime publishedOn,
                     Float lat, Float lon, Boolean paid, Integer participantLimit, Boolean requestModeration,
                     String title, String state, Long views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.initiator = initiator;
        this.description = description;
        this.eventDate = eventDate;
        this.createdOn = createdOn;
        this.publishedOn = publishedOn;
        this.lat = lat;
        this.lon = lon;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
        this.state = state;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public String getAnnotation() {
        return annotation;
    }

    public Category getCategory() {
        return category;
    }

    public Long getConfirmedRequests() {
        return confirmedRequests;
    }

    public User getInitiator() {
        return initiator;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
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

    public String getState() {
        return state;
    }

    public Long getViews() {
        return views;
    }
}
