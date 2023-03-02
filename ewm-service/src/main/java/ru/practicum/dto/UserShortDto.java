package ru.practicum.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь (краткая информация)
 */
public final class UserShortDto {

    @NotNull
    private final Long id;
    @NotNull
    private final String name;

    public UserShortDto(Long id, String name) {
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
        UserShortDto userShortDto = (UserShortDto) o;
        return Objects.equals(this.id, userShortDto.id) &&
                Objects.equals(this.name, userShortDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "class UserShortDto {\n" +
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
