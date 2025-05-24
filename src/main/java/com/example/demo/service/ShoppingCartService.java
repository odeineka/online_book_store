package com.example.demo.service;

import com.example.demo.dto.cart.CartItemResponseDto;
import com.example.demo.dto.cart.CreateCartItemRequestDto;
import com.example.demo.dto.cart.ShoppingCartResponseDto;
import com.example.demo.dto.cart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getCartForCurrentUser();

    CartItemResponseDto addItem(CreateCartItemRequestDto dto);

    CartItemResponseDto updateItem(Long cartItemId, UpdateCartItemRequestDto dto);

    void removeItem(Long cartItemId);
}
