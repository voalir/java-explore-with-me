package ru.practicum.dto;

import javax.validation.constraints.NotNull;

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

}
