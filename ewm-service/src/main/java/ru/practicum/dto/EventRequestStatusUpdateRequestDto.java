package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
public final class EventRequestStatusUpdateRequestDto {

    private final List<Long> requestIds;
    private final Status status;

    @JsonCreator
    public EventRequestStatusUpdateRequestDto(List<Long> requestIds, Status status) {
        this.requestIds = requestIds;
        this.status = status;
    }

    public List<Long> getRequestIds() {
        return requestIds;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Новый статус запроса на участие в событии текущего пользователя
     */
    public enum Status {
        CONFIRMED,
        REJECTED
    }
}
