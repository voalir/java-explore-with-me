package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.exception.AccessFailedException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.*;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        User user = getUserByIdRaw(userId);
        return requestRepository.findByRequester(user).stream().map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        checkUserAccessToAddRequest(user, event);
        ParticipationRequest participationRequest = new ParticipationRequest(
                null, LocalDateTime.now(), event, user,
                event.getRequestModeration() ? ParticipationRequestStatus.PENDING : ParticipationRequestStatus.CONFIRMED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        getUserByIdRaw(userId);
        ParticipationRequest participationRequest = getParticipationRequestByIdRaw(requestId);
        participationRequest.setStatus(ParticipationRequestStatus.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId) {
        User user = getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        checkUserAccessToEventRequest(user, event);
        return requestRepository.findByEvent(event).stream().map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        User user = getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        checkUserAccessToUpdateRequest(user, event);
        List<ParticipationRequest> requests = requestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());
        switch (eventRequestStatusUpdateRequest.getStatus()) {
            case REJECTED:
                rejectRequest(requests);
                break;
            case CONFIRMED:
                confirmRequest(event, requests);
                break;
        }
        return RequestMapper.toEventRequestStatusUpdateResult(requests);
    }

    public Integer getCountConfirmedRequestsByEventId(Long eventId) {
        return requestRepository.findByEvent_IdIsAndStatusIs(
                eventId, ParticipationRequestStatus.CONFIRMED).size();
    }

    private void confirmRequest(Event event, List<ParticipationRequest> requests) {
        List<ParticipationRequest> unconfirmedRequests = requests.stream()
                .filter(r -> r.getStatus() != ParticipationRequestStatus.CONFIRMED).collect(Collectors.toList());
        int freeLimit = event.getParticipantLimit() - getCountConfirmedRequestsByEventId(event.getId());

        if (event.getParticipantLimit() == 0 || event.getParticipantLimit() >= requests.size()) {
            requests.forEach(r -> r.setStatus(ParticipationRequestStatus.CONFIRMED));
        }

        if (unconfirmedRequests.size() <= freeLimit) {
            unconfirmedRequests.forEach(r -> r.setStatus(ParticipationRequestStatus.CONFIRMED));
        } else {
            unconfirmedRequests.subList(0, freeLimit - 1).forEach(r -> r.setStatus(ParticipationRequestStatus.CONFIRMED));
        }
    }

    private void rejectRequest(List<ParticipationRequest> requests) {
        requests.forEach(r -> r.setStatus(ParticipationRequestStatus.REJECTED));
    }

    private ParticipationRequest getParticipationRequestByIdRaw(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("request with id=" + requestId + " not found"));
    }

    private void checkUserAccessToUpdateRequest(User user, Event event) {
        if (!event.getInitiator().equals(user)) {
            throw new AccessFailedException("user with id=" + user.getId() + " can't change event data with id=" + event.getId());
        }
        if (event.getParticipantLimit() <=
                getCountConfirmedRequestsByEventId(event.getId())) {
            throw new AccessFailedException("event with id=" + event.getId() + " has max participants");
        }
    }

    private void checkUserAccessToAddRequest(User user, Event event) {
        if (event.getInitiator().equals(user)) {
            throw new AccessFailedException("user with id=" + user.getId() +
                    " initiator for event with id=" + event.getId());
        }
        if (requestRepository.findByEventAndRequester(event, user).size() > 0) {
            throw new AccessFailedException("user with id=" + user.getId() +
                    " have request to event with id=" + event.getId());
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new AccessFailedException("user with id=" + user.getId() +
                    " can't create request to unpublished event with id=" + event.getId());
        }
        if (event.getParticipantLimit() <=
                getCountConfirmedRequestsByEventId(event.getId())
        ) {
            throw new AccessFailedException("event with id=" + event.getId() + " has max participants");
        }
    }

    private void checkUserAccessToEventRequest(User user, Event event) {
        if (!event.getInitiator().equals(user)) {
            throw new AccessFailedException("user with id=" + user.getId() +
                    " not initiator for event with id=" + event.getId());
        }
    }

    private Event getEventByIdRaw(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("event with id=" + eventId + " not found"));
    }

    public User getUserByIdRaw(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user with id=" + userId + " not found"));
    }
}
