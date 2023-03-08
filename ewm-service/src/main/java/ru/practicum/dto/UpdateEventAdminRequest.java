package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение
 * этих данных не треубется.
 */
public final class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    @Size(min = 20, max = 7000)
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime eventDate;
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final StateActionEnum stateAction;
    @Size(min = 3, max = 120)
    private final String title;

    public UpdateEventAdminRequest(String annotation, Long category, String description, LocalDateTime eventDate,
                                   Location location, Boolean paid, Integer participantLimit, Boolean requestModeration,
                                   StateActionEnum stateAction, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.stateAction = stateAction;
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

    public StateActionEnum getStateAction() {
        return stateAction;
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
        UpdateEventAdminRequest updateEventAdminRequest = (UpdateEventAdminRequest) o;
        return Objects.equals(this.annotation, updateEventAdminRequest.annotation) &&
                Objects.equals(this.category, updateEventAdminRequest.category) &&
                Objects.equals(this.description, updateEventAdminRequest.description) &&
                Objects.equals(this.eventDate, updateEventAdminRequest.eventDate) &&
                Objects.equals(this.location, updateEventAdminRequest.location) &&
                Objects.equals(this.paid, updateEventAdminRequest.paid) &&
                Objects.equals(this.participantLimit, updateEventAdminRequest.participantLimit) &&
                Objects.equals(this.requestModeration, updateEventAdminRequest.requestModeration) &&
                Objects.equals(this.stateAction, updateEventAdminRequest.stateAction) &&
                Objects.equals(this.title, updateEventAdminRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit, requestModeration, stateAction, title);
    }

    @Override
    public String toString() {
        return "class UpdateEventAdminRequest {\n" +
                "    annotation: " + toIndentedString(annotation) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    eventDate: " + toIndentedString(eventDate) + "\n" +
                "    location: " + toIndentedString(location) + "\n" +
                "    paid: " + toIndentedString(paid) + "\n" +
                "    participantLimit: " + toIndentedString(participantLimit) + "\n" +
                "    requestModeration: " + toIndentedString(requestModeration) + "\n" +
                "    stateAction: " + toIndentedString(stateAction) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Новое состояние события
     */
    public enum StateActionEnum {
        PUBLISH_EVENT("PUBLISH_EVENT"),

        REJECT_EVENT("REJECT_EVENT");

        private final String value;

        StateActionEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StateActionEnum fromValue(String text) {
            for (StateActionEnum b : StateActionEnum.values()) {
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
