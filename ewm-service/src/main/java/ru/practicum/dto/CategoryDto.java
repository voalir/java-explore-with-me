package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryDto categoryDto = (CategoryDto) o;
        return Objects.equals(this.id, categoryDto.id) &&
                Objects.equals(this.name, categoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "class CategoryDto {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    name: " + toIndentedString(name) + "\n" +
                "}";
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
