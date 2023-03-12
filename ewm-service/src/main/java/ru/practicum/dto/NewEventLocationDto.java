package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public final class NewEventLocationDto {

    @NotBlank
    private final String name;
    @NotNull
    private final Double lat;
    @NotNull
    private final Double lon;
    @NotNull
    @Positive
    private final Double radius;//пока считаем, что это в относительных единицах

    @JsonCreator
    public NewEventLocationDto(String name, Double lat, Double lon, Double radius) {
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

    public String getName() {
        return name;
    }
}
