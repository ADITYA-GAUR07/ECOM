package com.ecommerce.ecom.service;

import com.ecommerce.ecom.payload.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
