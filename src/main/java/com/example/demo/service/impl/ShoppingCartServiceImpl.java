package com.example.demo.service.impl;

import com.example.demo.dto.cart.CreateCartItemRequestDto;
import com.example.demo.dto.cart.ShoppingCartResponseDto;
import com.example.demo.dto.cart.UpdateCartItemRequestDto;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mapper.CartItemMapper;
import com.example.demo.mapper.ShoppingCartMapper;
import com.example.demo.model.Book;
import com.example.demo.model.CartItem;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.repository.book.BookRepository;
import com.example.demo.repository.cartitem.CartItemRepository;
import com.example.demo.repository.shoppingcart.ShoppingCartRepository;
import com.example.demo.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final BookRepository bookRepo;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getCartForCurrentUser() {
        ShoppingCart cart = getCurrentUserCart();
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartResponseDto addItem(CreateCartItemRequestDto dto) {
        ShoppingCart cart = getCurrentUserCart();

        Book book = bookRepo.findById(dto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Book with ID %d not found", dto.bookId())));
        Optional<CartItem> existing = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();
        CartItem item = existing.orElseGet(() -> {
            CartItem newItem = cartItemMapper.toEntity(dto);
            newItem.setShoppingCart(cart);
            newItem.setBook(book);
            cart.getCartItems().add(newItem);
            return newItem;
        });

        item.setQuantity(existing.map(i -> i.getQuantity() + dto.quantity())
                .orElse(dto.quantity()));

        itemRepo.save(item);
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartResponseDto updateItem(Long cartItemId, UpdateCartItemRequestDto dto) {
        ShoppingCart cart = getCurrentUserCart();

        CartItem item = itemRepo.findByIdAndShoppingCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "CartItem with ID %d not found in the cart with ID %d",
                        cartItemId,cart.getId())));
        item.setQuantity(dto.quantity());
        itemRepo.save(item);
        return shoppingCartMapper.toDto(cartRepo.findById(cart.getId()).orElseThrow());
    }

    @Override
    public void removeItem(Long cartItemId) {
        ShoppingCart cart = getCurrentUserCart();
        CartItem item = itemRepo.findByIdAndShoppingCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "CartItem with ID %d not found in the cart with ID %d",
                        cartItemId,cart.getId())));
        itemRepo.delete(item);
    }

    @Override
    public ShoppingCart createCartForUser(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    private ShoppingCart getCurrentUserCart() {
        return cartRepo.findByUser(getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Cart for user with ID %d  not found", getCurrentUser().getId())));
    }
}
