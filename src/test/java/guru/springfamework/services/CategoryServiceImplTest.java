package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    @Mock
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @BeforeEach
    void setUp() throws Exception {
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(3,categoryDTOS.size());
        verify(categoryRepository,times(1)).findAll();

    }

    @Test
    void getCategoryByName() {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        //then
        assertEquals("Joe",categoryDTO.getName());
        assertEquals(1L,categoryDTO.getId());
        verify(categoryRepository,times(1)).findByName(anyString());
    }
}