package ru.practicum.model;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double lat;
    private Double lon;
    private Double radius;

    public EventLocation(Long id, String name, Double lat, Double lon, Double radius) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public EventLocation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
