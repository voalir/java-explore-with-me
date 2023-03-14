package ru.practicum.dto;

public final class EventLocationDto {

    private final Long id;
    private final String name;
    private final Double lat;
    private final Double lon;
    private final Double radius;

    public EventLocationDto(Long id, String name, Double lat, Double lon, Double radius) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Double getRadius() {
        return radius;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
