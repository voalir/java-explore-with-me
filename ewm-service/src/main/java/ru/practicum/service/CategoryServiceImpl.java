package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long catId) {

    }

    @Override
    public CategoryDto updateCategory(Integer catId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return null;
    }
}
