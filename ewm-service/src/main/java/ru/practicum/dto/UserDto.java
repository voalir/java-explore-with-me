package ru.practicum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Пользователь
 */
public final class UserDto {

    @Email
    @NotNull
    private final String email;
    private final Long id;
    @NotNull
    private final String name;

    public UserDto(String email, Long id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getEmail() {
        return email;
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
        UserDto userDto = (UserDto) o;
        return Objects.equals(this.email, userDto.email) &&
                Objects.equals(this.id, userDto.id) &&
                Objects.equals(this.name, userDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, name);
    }

    @Override
    public String toString() {
        return "class UserDto {\n" +
                "    email: " + toIndentedString(email) + "\n" +
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
