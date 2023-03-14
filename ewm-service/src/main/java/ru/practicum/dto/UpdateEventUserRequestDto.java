package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение
 * этих данных не требуется.
 */
public final class UpdateEventUserRequestDto {

    @Size(min = 20, max = 2000)
    private final String annotation;
    private final Long category;
    @Size(min = 20, max = 7000)
    private final String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime eventDate;
    private final LocationDto location;
    private final Boolean paid;
    private final Integer participantLimit;
    private final Boolean requestModeration;
    private final StateActionEnum stateAction;
    @Size(min = 3, max = 120)
    private final String title;

    @JsonCreator
    public UpdateEventUserRequestDto(String annotation, Long category, String description, LocalDateTime eventDate,
                                     LocationDto locationDto, Boolean paid, Integer participantLimit, Boolean requestModeration,
                                     StateActionEnum stateAction, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = locationDto;
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

    public LocationDto getLocation() {
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
