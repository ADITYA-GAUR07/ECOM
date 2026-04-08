package com.ecommerce.ecom.repository;

import com.ecommerce.ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
