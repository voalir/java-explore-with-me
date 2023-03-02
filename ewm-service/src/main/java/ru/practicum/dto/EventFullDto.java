package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * EventFullDto
 */
public final class EventFullDto {
    private final String annotation;
    private final CategoryDto category;
    private final Long confirmedRequests;
    private final String createdOn;
    private final String description;
    private final String eventDate;
    private final Long id;
    private final UserShortDto initiator;
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final String publishedOn;
    private final Boolean requestModeration;
    private final StateEnum state;
    private final String title;
    private final Long views;

    public EventFullDto(String annotation, CategoryDto category, Long confirmedRequests, String createdOn,
                        String description, String eventDate, Long id, UserShortDto initiator, Location location,
                        Boolean paid, Integer participantLimit, String publishedOn, Boolean requestModeration,
                        StateEnum state, String title, Long views) {
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.location = location;
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

    public String getCreatedOn() {
        return createdOn;
    }

    public String getDescription() {
        return description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public Long getId() {
        return id;
    }

    public UserShortDto getInitiator() {
        return initiator;
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

    public String getPublishedOn() {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventFullDto eventFullDto = (EventFullDto) o;
        return Objects.equals(this.annotation, eventFullDto.annotation) &&
                Objects.equals(this.category, eventFullDto.category) &&
                Objects.equals(this.confirmedRequests, eventFullDto.confirmedRequests) &&
                Objects.equals(this.createdOn, eventFullDto.createdOn) &&
                Objects.equals(this.description, eventFullDto.description) &&
                Objects.equals(this.eventDate, eventFullDto.eventDate) &&
                Objects.equals(this.id, eventFullDto.id) &&
                Objects.equals(this.initiator, eventFullDto.initiator) &&
                Objects.equals(this.location, eventFullDto.location) &&
                Objects.equals(this.paid, eventFullDto.paid) &&
                Objects.equals(this.participantLimit, eventFullDto.participantLimit) &&
                Objects.equals(this.publishedOn, eventFullDto.publishedOn) &&
                Objects.equals(this.requestModeration, eventFullDto.requestModeration) &&
                Objects.equals(this.state, eventFullDto.state) &&
                Objects.equals(this.title, eventFullDto.title) &&
                Objects.equals(this.views, eventFullDto.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, confirmedRequests, createdOn, description, eventDate, id, initiator, location, paid, participantLimit, publishedOn, requestModeration, state, title, views);
    }

    @Override
    public String toString() {
        return "class EventFullDto {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    createdOn: " + toIndentedString(createdOn) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    initiator: " + toIndentedString(initiator) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
                "    publishedOn: " + toIndentedString(publishedOn) + "\n" +
                "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
                "    state: " + toIndentedString(state) + "\n" +
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

    /**
     * Список состояний жизненного цикла события
     */
    public enum StateEnum {
        PENDING("PENDING"),

        PUBLISHED("PUBLISHED"),

        CANCELED("CANCELED");

        private final String value;

        StateEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StateEnum fromValue(String text) {
            for (StateEnum b : StateEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
