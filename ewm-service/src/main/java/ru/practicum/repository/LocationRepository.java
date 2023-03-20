package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EventLocation;

public interface LocationRepository extends JpaRepository<EventLocation, Long> {

}
