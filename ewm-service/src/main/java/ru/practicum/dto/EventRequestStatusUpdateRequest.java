package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Objects;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
public final class EventRequestStatusUpdateRequest {

    private final List<Long> requestIds;
    private final StatusEnum status;

    public EventRequestStatusUpdateRequest(List<Long> requestIds, StatusEnum status) {
        this.requestIds = requestIds;
        this.status = status;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest = (EventRequestStatusUpdateRequest) o;
        return Objects.equals(this.requestIds, eventRequestStatusUpdateRequest.requestIds) &&
                Objects.equals(this.status, eventRequestStatusUpdateRequest.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIds, status);
    }

    @Override
    public String toString() {
        return "class EventRequestStatusUpdateRequest {\n" +
                "    requestIds: " + toIndentedString(requestIds) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
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
