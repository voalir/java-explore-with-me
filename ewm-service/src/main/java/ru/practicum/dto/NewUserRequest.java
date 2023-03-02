package ru.practicum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Данные нового пользователя
 */
public final class NewUserRequest {

    @Email
    @NotNull
    private final String email;
    @NotNull
    private final String name;

    public NewUserRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
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
        NewUserRequest newUserRequest = (NewUserRequest) o;
        return Objects.equals(this.email, newUserRequest.email) &&
                Objects.equals(this.name, newUserRequest.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }

    @Override
    public String toString() {
        return "class NewUserRequest {\n" +
                "    email: " + toIndentedString(email) + "\n" +
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
