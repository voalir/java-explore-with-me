package ru.practicum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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

}
