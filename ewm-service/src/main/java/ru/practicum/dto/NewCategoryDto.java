package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

/**
 * Данные для добавления новой категории
 */
public final class NewCategoryDto {

    @NotBlank
    private final String name;

    @JsonCreator
    public NewCategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
