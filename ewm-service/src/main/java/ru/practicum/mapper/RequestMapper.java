package ru.practicum.mapper;

import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.ParticipationRequestStatus;

import java.util.ArrayList;
import java.util.List;

public final class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(
                participationRequest.getCreated(), participationRequest.getEvent().getId(),
                participationRequest.getId(), participationRequest.getRequester().getId(),
                participationRequest.getStatus().name());
    }

    public static EventRequestStatusUpdateResult toEventRequestStatusUpdateResult(List<ParticipationRequest> requests) {
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (ParticipationRequest participationRequest : requests) {
            if (participationRequest.getStatus() == ParticipationRequestStatus.CONFIRMED) {
                confirmedRequests.add(toParticipationRequestDto(participationRequest));
            }
            if (participationRequest.getStatus() == ParticipationRequestStatus.REJECTED) {
                rejectedRequests.add(toParticipationRequestDto(participationRequest));
            }
        }
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }
}
