package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение
 * этих данных не требуется.
 */
public final class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    @Size(min = 20, max = 7000)
    private final String description;
    private final String eventDate;
    private final Location location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final StateActionEnum stateAction;
    @Size(min = 3, max = 120)
    private final String title;

    public UpdateEventUserRequest(String annotation, Long category, String description, String eventDate,
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateEventUserRequest updateEventUserRequest = (UpdateEventUserRequest) o;
        return Objects.equals(this.annotation, updateEventUserRequest.annotation) &&
                Objects.equals(this.category, updateEventUserRequest.category) &&
                Objects.equals(this.description, updateEventUserRequest.description) &&
                Objects.equals(this.eventDate, updateEventUserRequest.eventDate) &&
                Objects.equals(this.location, updateEventUserRequest.location) &&
                Objects.equals(this.paid, updateEventUserRequest.paid) &&
                Objects.equals(this.participantLimit, updateEventUserRequest.participantLimit) &&
                Objects.equals(this.requestModeration, updateEventUserRequest.requestModeration) &&
                Objects.equals(this.stateAction, updateEventUserRequest.stateAction) &&
                Objects.equals(this.title, updateEventUserRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit,
                requestModeration, stateAction, title);
    }

    @Override
    public String toString() {
        return "class UpdateEventUserRequest {\n" +
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
     * Изменение состояния события
     */
    public enum StateActionEnum {
        SEND_TO_REVIEW("SEND_TO_REVIEW"),

        CANCEL_REVIEW("CANCEL_REVIEW");

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
