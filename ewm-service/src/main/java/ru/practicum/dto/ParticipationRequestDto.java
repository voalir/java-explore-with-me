package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */
public final class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime created;
    private final Long event;
    private final Long id;
    private final Long requester;
    private final String status;

    public ParticipationRequestDto(LocalDateTime created, Long event, Long id, Long requester, String status) {
        this.created = created;
        this.event = event;
        this.id = id;
        this.requester = requester;
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Long getEvent() {
        return event;
    }

    public Long getId() {
        return id;
    }

    public Long getRequester() {
        return requester;
    }

    public String getStatus() {
        return status;
    }

}
