package ru.practicum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Данные нового пользователя
 */
public final class NewUserRequestDto {

    @Email
    @NotNull
    private final String email;
    @NotNull
    private final String name;

    public NewUserRequestDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
