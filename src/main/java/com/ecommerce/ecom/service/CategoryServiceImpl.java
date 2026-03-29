package com.ecommerce.ecom.service;

import com.ecommerce.ecom.exceptions.APIException;
import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponseDTO;
import com.ecommerce.ecom.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No categories created!");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setContent(categoryDTOS);

        categoryResponseDTO.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDTO.setTotalElements(categoryPage.getTotalElements());
        categoryResponseDTO.setPageNumber(categoryPage.getNumber());
        categoryResponseDTO.setPageSize(categoryPage.getSize());
        categoryResponseDTO.setLastPage(categoryPage.isLast());

        return categoryResponseDTO;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);

        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if (categoryFromDb != null) {
            throw new APIException("Category with name: " +  category.getCategoryName() + " already exists");
        }

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        CategoryDTO deletingCategory = modelMapper.map(category, CategoryDTO.class);
        categoryRepository.delete(category);
        return deletingCategory;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category categoryFromDb = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryFromDb.setCategoryName(categoryDTO.getCategoryName());

        Category savedCategory = categoryRepository.save(categoryFromDb);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
