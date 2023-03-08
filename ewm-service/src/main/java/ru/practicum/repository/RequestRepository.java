package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.ParticipationRequestStatus;
import ru.practicum.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequester(User user);

    List<ParticipationRequest> findByEventAndRequester(Event event, User user);

    @Query("select r from ParticipationRequest r where r.id in :events and r.status = :status")
    List<ParticipationRequest> getRequestsByStatusAndEventIds(List<Long> events, ParticipationRequestStatus status);

    @Query("select r from ParticipationRequest r where r.id = :eventId and r.status = :status")
    List<ParticipationRequest> getRequestsByStatusAndEventId(Long eventId, ParticipationRequestStatus status);

}
