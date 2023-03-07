package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
