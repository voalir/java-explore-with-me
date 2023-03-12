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

    List<ParticipationRequest> findByEvent(Event event);

    @Query("select new java.lang.Boolean(count(*) > 0) from ParticipationRequest " +
            "where event = ?1 and requester = ?2")
    Boolean hasRequestToEventByUser(Event event, User user);

    Integer countByEvent_IdIsAndStatusIs(Long eventId, ParticipationRequestStatus status);

    List<ParticipationRequest> findByEvent_IdInAndStatusIs(List<Long> eventIds, ParticipationRequestStatus status);

}
