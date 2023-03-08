package ru.practicum.mapper;

import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.Location;
import ru.practicum.dto.NewEventDto;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;

import java.time.LocalDateTime;

public final class EventMapper {

    public static EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views) {
        return new EventFullDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getCreatedOn(), event.getDescription(), event.getEventDate(),
                event.getId(), UserMapper.toUserShortDto(event.getInitiator()), new Location(event.getLat(), event.getLon()),
                event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(), event.getRequestModeration(),
                EventFullDto.StateEnum.valueOf(event.getState().name()), event.getTitle(), views);
    }

    public static EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getCreatedOn(), event.getId(), UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(), event.getTitle(), views);
    }

    public static Event toEvent(NewEventDto newEventDto, User user, Category category) {
        return new Event(null, newEventDto.getAnnotation(), category, user, newEventDto.getDescription(),
                newEventDto.getEventDate(), LocalDateTime.now(), null, newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon(), newEventDto.getPaid(), newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(), EventState.PENDING, newEventDto.getTitle());
    }
}
