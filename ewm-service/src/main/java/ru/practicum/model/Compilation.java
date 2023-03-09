package ru.practicum.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Event> events;
    private Boolean pinned;
    private String title;

    public Compilation(Long id, List<Event> events, Boolean pinned, String title) {
        this.id = id;
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }

    public Compilation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
