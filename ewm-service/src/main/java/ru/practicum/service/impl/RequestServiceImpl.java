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
import ru.practicum.service.RequestService;
import ru.practicum.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserService userService;

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        User user = userService.getUserByIdRaw(userId);
        return requestRepository.findByRequester(user).stream().map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = userService.getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        checkUserAccessToAddRequest(user, event);
        ParticipationRequest participationRequest = new ParticipationRequest(
                null, LocalDateTime.now(), event, user,
                event.getRequestModeration() ? ParticipationRequestStatus.PENDING : ParticipationRequestStatus.CONFIRMED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User user = userService.getUserByIdRaw(userId);
        ParticipationRequest participationRequest = getParticipationRequestByIdRaw(requestId);
        participationRequest.setStatus(ParticipationRequestStatus.REJECTED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId) {
        User user = userService.getUserByIdRaw(userId);
        Event event = getEventByIdRaw(eventId);
        return requestRepository.findByEventAndRequester(event, user).stream().map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatus(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        User user = userService.getUserByIdRaw(userId);
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

    @Override
    public Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events) {
        return requestRepository.findByEvent_IdInAndStatusIs(
                        events, ParticipationRequestStatus.CONFIRMED).
                stream().collect(Collectors.groupingBy(pr -> pr.getEvent().getId(), Collectors.counting()));
    }

    @Override
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
                //(requestRepository.findByEvent(event).size()
                //        - requestRepository.getRequestsByStatusAndEventId(event.getId(),
                //        String.valueOf(ParticipationRequestStatus.REJECTED.ordinal())).size())
                getCountConfirmedRequestsByEventId(event.getId())
        ) {
            throw new AccessFailedException("event with id=" + event.getId() + " has max participants");
        }

    }

    private Event getEventByIdRaw(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("event with id=" + eventId + " not found"));
    }
}
