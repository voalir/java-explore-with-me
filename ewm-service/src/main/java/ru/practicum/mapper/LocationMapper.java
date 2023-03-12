package ru.practicum.mapper;

import ru.practicum.dto.EventLocationDto;
import ru.practicum.dto.NewEventLocationDto;
import ru.practicum.model.EventLocation;

public final class LocationMapper {

    public static EventLocation toEventLocation(EventLocationDto eventLocationDto) {
        return new EventLocation(null, eventLocationDto.getName(), eventLocationDto.getLat(),
                eventLocationDto.getLon(), eventLocationDto.getRadius());
    }

    public static EventLocation toEventLocation(NewEventLocationDto newEventLocationDto) {
        return new EventLocation(null, newEventLocationDto.getName(), newEventLocationDto.getLat(),
                newEventLocationDto.getLon(), newEventLocationDto.getRadius());
    }

    public static EventLocationDto toEventLocationDto(EventLocation eventLocation) {
        return new EventLocationDto(eventLocation.getId(), eventLocation.getName(), eventLocation.getLat(),
                eventLocation.getLon(), eventLocation.getRadius());
    }

}
