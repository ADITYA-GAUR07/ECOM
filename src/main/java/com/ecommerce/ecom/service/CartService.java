package com.ecommerce.ecom.service;

import com.ecommerce.ecom.payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();
}
