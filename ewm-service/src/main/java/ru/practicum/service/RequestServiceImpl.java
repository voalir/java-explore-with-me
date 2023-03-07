package ru.practicum.service;

import org.springframework.stereotype.Service;
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
}
