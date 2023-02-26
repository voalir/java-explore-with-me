package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * EndpointHit
 */
public class EndpointHitDto {

    private Long id;
    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
