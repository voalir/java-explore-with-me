package ru.practicum.model;

import java.util.Objects;

/**
 * ViewStats
 */
public class ViewStatsDto {

    private String app;
    private String uri;
    private Long hits;

    public ViewStatsDto(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
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

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
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
