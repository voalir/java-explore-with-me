package ru.practicum.model;

import java.util.Objects;

/**
 * ViewStats
 */
public class ViewStatsDto {

    private String app = null;
    private String uri = null;
    private Long hits = null;

    public ViewStatsDto app(String app) {
        this.app = app;
        return this;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public ViewStatsDto uri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ViewStatsDto hits(Long hits) {
        this.hits = hits;
        return this;
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
