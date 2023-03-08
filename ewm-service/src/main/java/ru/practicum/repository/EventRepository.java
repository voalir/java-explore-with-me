package ru.practicum.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;
import ru.practicum.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    //TODO - add query jpql
    List<Event> findEventsByFilter(List<Long> users, List<String> states, List<Long> categories,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    List<Event> findByUser(User user, PageRequest pageRequest);

    Event findEventByIdAndUser(Long eventId, User user);

    List<Event> findEventsByFilter(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, PageRequest pageRequest);

    Event findEventByIdAndPublished(Long eventId);

    Integer getCountConfirmedRequestsByEvent(Event event);
}
