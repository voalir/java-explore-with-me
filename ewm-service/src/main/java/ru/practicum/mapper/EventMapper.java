package ru.practicum.mapper;

import ru.practicum.dto.*;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;

import java.time.LocalDateTime;

public final class EventMapper {

    public static EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views) {
        return new EventFullDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getCreatedOn(), event.getDescription(), event.getEventDate(),
                event.getId(), UserMapper.toUserShortDto(event.getInitiator()), new LocationDto(event.getLat(), event.getLon()),
                event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(), event.getRequestModeration(),
                EventFullDto.State.valueOf(event.getState().name()), event.getTitle(), views);
    }

    public static EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views) {
        return new EventShortDto(event.getAnnotation(), CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests, event.getEventDate(), event.getId(), UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(), event.getTitle(), views);
    }

    public static Event toEvent(NewEventDto newEventDto, User user, Category category) {
        return new Event(null, newEventDto.getAnnotation(), category, user, newEventDto.getDescription(),
                newEventDto.getEventDate(), LocalDateTime.now(), null, newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon(), newEventDto.getPaid(), newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(), EventState.PENDING, newEventDto.getTitle());
    }

    public static EventState toEventState(UpdateEventAdminRequestDto.StateAction stateAction) {
        switch (stateAction) {
            case PUBLISH_EVENT:
                return EventState.PUBLISHED;
            case REJECT_EVENT:
                return EventState.CANCELED;
            default:
                return EventState.PENDING;
        }
    }

    public static EventState toEventState(UpdateEventUserRequestDto.StateAction stateAction) {
        switch (stateAction) {
            case CANCEL_REVIEW:
                return EventState.CANCELED;
            case SEND_TO_REVIEW:
                return EventState.PENDING;
            default:
                return EventState.PUBLISHED;
        }
    }

    public static EventState toEventState(EventFullDto.State stateAction) {
        switch (stateAction) {
            case PUBLISHED:
                return EventState.PUBLISHED;
            case CANCELED:
                return EventState.CANCELED;
            default:
                return EventState.PENDING;
        }
    }
}
