package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewCategoryDto newCategoryDto = (NewCategoryDto) o;
        return Objects.equals(this.name, newCategoryDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "class NewCategoryDto {\n" +
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
