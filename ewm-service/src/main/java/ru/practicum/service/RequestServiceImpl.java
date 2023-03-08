package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }
}
