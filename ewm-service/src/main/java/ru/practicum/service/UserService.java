package ru.practicum.service;

import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto addUser(NewUserRequest newUserRequest);

    void deleteUser(Long userId);

    User getUserByIdRaw(Long userId);
}
