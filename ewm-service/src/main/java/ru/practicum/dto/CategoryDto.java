package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

/**
 * Категория
 */

public final class CategoryDto {

    private final Long id;
    @NotBlank
    private final String name;

    @JsonCreator
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
