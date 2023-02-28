package ru.practicum.model;

public class ViewStat {
    private final String app;
    private final String uri;
    private final Long hits;

    public ViewStat(String app, String uri, Long hits) {
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

}
