package com.ecommerce.ecom.service;

import com.ecommerce.ecom.payload.CategoryDTO;
import com.ecommerce.ecom.payload.CategoryResponseDTO;


public interface CategoryService {

    CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize,  String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO );

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
