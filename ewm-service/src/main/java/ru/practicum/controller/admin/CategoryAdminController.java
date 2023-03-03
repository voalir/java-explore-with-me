package ru.practicum.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;

import javax.validation.Valid;

@RestController("/admin/categories")
public class CategoryAdminController {

    @PostMapping
    ResponseEntity<Object> addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        throw new RuntimeException("not implemented");
    }

    @DeleteMapping("/{catId}")
    ResponseEntity<Object> deleteCategory(@PathVariable Long catId) {
        throw new RuntimeException("not implemented");
    }

    @PatchMapping("/{catId}")
    ResponseEntity<Object> pathUser(@PathVariable Integer catId,
                                    @Valid @RequestBody CategoryDto categoryDto) {
        throw new RuntimeException("not implemented");
    }

}
