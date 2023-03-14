package ru.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.NewUserRequestDto;
import ru.practicum.dto.UserDto;
import ru.practicum.mapper.UserMapper;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return userRepository.findAllByIdIn(ids, pageRequest).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto addUser(NewUserRequestDto newUserRequestDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequestDto)));
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
