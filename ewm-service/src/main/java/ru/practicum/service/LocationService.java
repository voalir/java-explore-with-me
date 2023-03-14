package ru.practicum.service;

import ru.practicum.dto.EventLocationDto;
import ru.practicum.dto.NewEventLocationDto;

import java.util.List;

public interface LocationService {

    EventLocationDto addLocation(NewEventLocationDto newEventLocationDto);

    void deleteLocation(Long locationId);

    EventLocationDto updateLocation(Long locationId, EventLocationDto updatedEventLocationDto);

    List<EventLocationDto> getEventLocations(List<Long> locationIds, Integer from, Integer size);
}
