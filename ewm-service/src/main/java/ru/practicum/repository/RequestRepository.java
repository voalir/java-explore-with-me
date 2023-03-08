package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findByUser(User user);

    List<ParticipationRequest> findAllByEventAndUser(User user, Event event);

    Map<Long, Long> getCountConfirmedRequestsByEventIds(List<Long> events);

}
