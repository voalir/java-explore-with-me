package ru.practicum.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@Validated
public class CategoryAdminController {

    private final Logger logger = LoggerFactory.getLogger(CategoryAdminController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        logger.debug("add category");
        return categoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable Long catId) {
        logger.debug("delete category " + catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    CategoryDto pathUser(@PathVariable Long catId,
                         @RequestBody @Valid CategoryDto categoryDto) {
        logger.debug("update category " + catId);
        return categoryService.updateCategory(catId, categoryDto);
    }

}
