package ru.practicum.service;

import ru.practicum.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResultDto updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto);

    Integer getCountConfirmedRequestsByEventId(Long eventId);
}
