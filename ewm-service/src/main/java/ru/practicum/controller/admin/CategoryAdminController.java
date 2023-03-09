package ru.practicum.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {

    Logger logger = LoggerFactory.getLogger(CategoryAdminController.class);

    @Autowired
    CategoryService categoryService;

    @PostMapping
    CategoryDto addCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        logger.debug("add category");
        return categoryService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    void deleteCategory(@PathVariable Long catId) {
        logger.debug("delete category " + catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    CategoryDto pathUser(@PathVariable Long catId,
                         @Valid @RequestBody CategoryDto categoryDto) {
        logger.debug("update category " + catId);
        return categoryService.updateCategory(catId, categoryDto);
    }

}
