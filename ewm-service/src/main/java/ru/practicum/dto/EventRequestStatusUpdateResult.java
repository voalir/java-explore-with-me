package ru.practicum.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = (EventRequestStatusUpdateResult) o;
        return Objects.equals(this.confirmedRequests, eventRequestStatusUpdateResult.confirmedRequests) &&
                Objects.equals(this.rejectedRequests, eventRequestStatusUpdateResult.rejectedRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmedRequests, rejectedRequests);
    }

    @Override
    public String toString() {
        return "class EventRequestStatusUpdateResult {\n" +
                "    confirmedRequests: " + toIndentedString(confirmedRequests) + "\n" +
                "    rejectedRequests: " + toIndentedString(rejectedRequests) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
