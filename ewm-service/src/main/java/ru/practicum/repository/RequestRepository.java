package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.ParticipationRequestStatus;
import ru.practicum.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequester(User user);

    List<ParticipationRequest> findByEvent(Event event);

    List<ParticipationRequest> findByEventAndRequester(Event event, User user);

    List<ParticipationRequest> findByEvent_IdIsAndStatusIs(Long eventId, ParticipationRequestStatus status);

    List<ParticipationRequest> findByEvent_IdInAndStatusIs(List<Long> eventIds, ParticipationRequestStatus status);

}
