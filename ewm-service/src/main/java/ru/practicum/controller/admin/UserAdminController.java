package ru.practicum.controller.admin;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/admin/users")
public class UserAdminController {
    @GetMapping
    List<UserDto> getUsers(@RequestParam List<Integer> ids,
                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        throw new RuntimeException("not implemented");
    }

    @PostMapping
    UserDto addUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        throw new RuntimeException("not implemented");
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Long userId) {
        throw new RuntimeException("not implemented");
    }
}
