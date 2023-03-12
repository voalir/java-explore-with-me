package ru.practicum.dto;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 */
public final class EventRequestStatusUpdateResult {

    private final List<ParticipationRequestDto> confirmedRequests;

    private final List<ParticipationRequestDto> rejectedRequests;

    public EventRequestStatusUpdateResult(List<ParticipationRequestDto> confirmedRequests, List<ParticipationRequestDto> rejectedRequests) {
        this.confirmedRequests = confirmedRequests;
        this.rejectedRequests = rejectedRequests;
    }

    public List<ParticipationRequestDto> getConfirmedRequests() {
        return confirmedRequests;
    }

    public List<ParticipationRequestDto> getRejectedRequests() {
        return rejectedRequests;
    }
}
