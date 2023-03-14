package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
public final class EventRequestStatusUpdateRequestDto {

    private final List<Long> requestIds;
    private final StatusEnum status;

    @JsonCreator
    public EventRequestStatusUpdateRequestDto(List<Long> requestIds, StatusEnum status) {
        this.requestIds = requestIds;
        this.status = status;
    }

    public List<Long> getRequestIds() {
        return requestIds;
    }

    public StatusEnum getStatus() {
        return status;
    }

    /**
     * Новый статус запроса на участие в событии текущего пользователя
     */
    public enum StatusEnum {
        CONFIRMED("CONFIRMED"),

        REJECTED("REJECTED");

        private final String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
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
