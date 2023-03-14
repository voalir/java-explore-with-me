package ru.practicum.dto;

/**
 * Широта и долгота места проведения события
 */
public final class LocationDto {

    private final Float lat;
    private final Float lon;

    public LocationDto(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }
}
