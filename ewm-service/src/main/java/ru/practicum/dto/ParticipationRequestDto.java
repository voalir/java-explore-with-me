package ru.practicum.dto;

import java.util.Objects;

/**
 * Заявка на участие в событии
 */
public final class ParticipationRequestDto {

    private final String created;
    private final Long event;
    private final Long id;
    private final Long requester;
    private final String status;

    public ParticipationRequestDto(String created, Long event, Long id, Long requester, String status) {
        this.created = created;
        this.event = event;
        this.id = id;
        this.requester = requester;
        this.status = status;
    }

    public String getCreated() {
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequestDto participationRequestDto = (ParticipationRequestDto) o;
        return Objects.equals(this.created, participationRequestDto.created) &&
                Objects.equals(this.event, participationRequestDto.event) &&
                Objects.equals(this.id, participationRequestDto.id) &&
                Objects.equals(this.requester, participationRequestDto.requester) &&
                Objects.equals(this.status, participationRequestDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, event, id, requester, status);
    }

    @Override
    public String toString() {
        return "class ParticipationRequestDto {\n" +
                "    created: " + toIndentedString(created) + "\n" +
                "    event: " + toIndentedString(event) + "\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    requester: " + toIndentedString(requester) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
