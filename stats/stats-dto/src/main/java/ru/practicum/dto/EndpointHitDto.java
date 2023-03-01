package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * EndpointHit
 */
public final class EndpointHitDto {

    private final Long id;
    @NotBlank
    private final String app;
    @NotBlank
    private final String uri;
    @NotBlank
    private final String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime timestamp;

    public EndpointHitDto(Long id, String app, String uri, String ip, LocalDateTime timeStamp) {
        this.id = id;
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    public String getApp() {
        return app;
    }

    public String getUri() {
        return uri;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndpointHitDto endpointHitDto = (EndpointHitDto) o;
        return Objects.equals(this.id, endpointHitDto.id) &&
                Objects.equals(this.app, endpointHitDto.app) &&
                Objects.equals(this.uri, endpointHitDto.uri) &&
                Objects.equals(this.ip, endpointHitDto.ip) &&
                Objects.equals(this.timestamp, endpointHitDto.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip, timestamp);
    }

    @Override
    public String toString() {

        return "class EndpointHit {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    app: " + toIndentedString(app) + "\n" +
                "    uri: " + toIndentedString(uri) + "\n" +
                "    ip: " + toIndentedString(ip) + "\n" +
                "    timestamp: " + toIndentedString(timestamp) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
