package ru.practicum.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@SqlResultSetMapping(name = "ToViewStatsDtoMapping",
        classes = {
                @ConstructorResult(
                        targetClass = ViewStatsDto.class,
                        columns = {
                                @ColumnResult(name = "app", type = String.class),
                                @ColumnResult(name = "uri", type = String.class),
                                @ColumnResult(name = "hits", type = Long.class)
                        }
                )})
@NamedNativeQueries({
        @NamedNativeQuery(name = "getViewStatsUniqueByUrls",
                query = "select distinct hits.app, hits.uri, count(hits.id) as hits from hits " +
                        "where hits.timestamp between ?1 and ?2 " +
                        "and hits.uri in (?3)" +
                        "group by hits.app, hits.uri order by hits desc",
                resultSetMapping = "ToViewStatsDtoMapping"),
        @NamedNativeQuery(name = "getViewStatsByUrls",
                query = "select hits.app, hits.uri, count(hits.id) as hits from hits " +
                        "where hits.timestamp between ?1 and ?2 " +
                        "and hits.uri in (?3)" +
                        "group by hits.app, hits.uri order by hits desc",
                resultSetMapping = "ToViewStatsDtoMapping"),
        @NamedNativeQuery(name = "getViewStatsUnique",
                query = "select distinct hits.app, hits.uri, count(hits.id) as hits from hits " +
                        "where hits.timestamp between ?1 and ?2 " +
                        "group by hits.app, hits.uri order by hits desc",
                resultSetMapping = "ToViewStatsDtoMapping"),
        @NamedNativeQuery(name = "getViewStats",
                query = "select hits.app, hits.uri, count(hits.id) as hits from hits " +
                        "where hits.timestamp between ?1 and ?2 " +
                        "group by hits.app, hits.uri order by hits desc",
                resultSetMapping = "ToViewStatsDtoMapping")}
)
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;

    public Hit(String app, String uri, String ip, LocalDateTime timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }

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
}
