package com.ecommerce.ecom.repository;

import com.ecommerce.ecom.model.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotEmpty @Size(min = 5, message = "Must be more than 5 characters") String categoryName);
}
