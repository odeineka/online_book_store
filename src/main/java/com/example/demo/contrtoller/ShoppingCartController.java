package com.example.demo.contrtoller;

import com.example.demo.dto.cart.CartItemResponseDto;
import com.example.demo.dto.cart.CreateCartItemRequestDto;
import com.example.demo.dto.cart.ShoppingCartResponseDto;
import com.example.demo.dto.cart.UpdateCartItemRequestDto;
import com.example.demo.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get current user's shopping cart")
    public ShoppingCartResponseDto getCart() {
        return cartService.getCartForCurrentUser();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add a book to shopping cart")

    public CartItemResponseDto addItem(
            @Valid @RequestBody CreateCartItemRequestDto dto) {
        return cartService.addItem(dto);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update quantity of a cart item")
    public CartItemResponseDto updateItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDto dto) {
        return cartService.updateItem(cartItemId, dto);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove an item from shopping cart")
    public void removeItem(@PathVariable Long cartItemId) {
        cartService.removeItem(cartItemId);
    }
}
