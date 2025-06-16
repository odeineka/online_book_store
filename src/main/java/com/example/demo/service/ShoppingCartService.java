package com.example.demo.service;

import com.example.demo.dto.cart.CreateCartItemRequestDto;
import com.example.demo.dto.cart.ShoppingCartResponseDto;
import com.example.demo.dto.cart.UpdateCartItemRequestDto;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getCartForCurrentUser();

    ShoppingCartResponseDto addItem(CreateCartItemRequestDto dto);

    ShoppingCartResponseDto updateItem(Long cartItemId, UpdateCartItemRequestDto dto);

    void removeItem(Long cartItemId);

    ShoppingCart createCartForUser(User user);
}
