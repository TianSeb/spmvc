package tianseb.mvcrest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tianseb.mvcrest.api.v1.model.CategoryDTO;
import tianseb.mvcrest.api.v1.model.CategoryListDTO;
import tianseb.mvcrest.services.CategoryService;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO allCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO categoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}
