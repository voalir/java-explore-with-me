package ru.practicum.controller.publicity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController("/categories")
public class CategoryPublicController {

    @GetMapping
    List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/{catId}")
    CategoryDto getCategoryById(@PathVariable Long catId) {
        throw new RuntimeException("not implemented");
    }
}
