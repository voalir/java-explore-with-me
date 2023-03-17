package ru.practicum.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    List<Event> findByInitiator(User user, PageRequest pageRequest);

    @Query(value = "select events.* from events " +
            "join locations on " +
            "distance(events.lat, events.lon, locations.lat, locations.lon) <= locations.radius " +
            "where locations.id = ?", nativeQuery = true)
    List<Event> getEventsByLocation(Long eventLocation, PageRequest pageRequest);
}
