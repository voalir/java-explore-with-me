package ru.practicum.mapper;

import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.dto.UserShortDto;
import ru.practicum.model.User;

public class UserMapper {

    public static User toUser(UserDto userDto) {
        return new User(null, userDto.getName(), userDto.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getEmail(), user.getId(), user.getName());
    }

    public static User toUser(NewUserRequest newUserRequest) {
        return new User(null, newUserRequest.getName(), newUserRequest.getEmail());
    }
}
