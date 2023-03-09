package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return userRepository.findAll(pageRequest).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByIdRaw(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user with id=" + userId + " not found"));
    }
}
