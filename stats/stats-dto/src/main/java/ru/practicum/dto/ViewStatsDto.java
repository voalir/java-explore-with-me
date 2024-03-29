package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * ViewStats
 */
public final class ViewStatsDto {

    private final String app;
    private final String uri;
    private final Long hits;

    @JsonCreator
    public ViewStatsDto(@JsonProperty("app") String app, @JsonProperty("uri") String uri, @JsonProperty("hits") Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }

    public String getApp() {
        return app;
    }

    public String getUri() {
        return uri;
    }

    public Long getHits() {
        return hits;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewStatsDto viewStatsDto = (ViewStatsDto) o;
        return Objects.equals(this.app, viewStatsDto.app) &&
                Objects.equals(this.uri, viewStatsDto.uri) &&
                Objects.equals(this.hits, viewStatsDto.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, hits);
    }

    @Override
    public String toString() {

        return "class ViewStats {\n" +
                "    app: " + toIndentedString(app) + "\n" +
                "    uri: " + toIndentedString(uri) + "\n" +
                "    hits: " + toIndentedString(hits) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
