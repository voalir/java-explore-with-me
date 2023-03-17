package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EventLocation;

public interface LocationRepository extends JpaRepository<EventLocation, Long> {
    @Query(value = "select COUNT(*) > 0 " +
            "from locations where " +
            "distance(?, ?, locations.lat, locations.lon) <= locations.radius", nativeQuery = true)
    boolean isReferToAnyLocation(Float lat, Float lon);
}
