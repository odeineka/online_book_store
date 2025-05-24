package com.example.demo.service.impl;

import com.example.demo.dto.cart.CartItemResponseDto;
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
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private ShoppingCart getCurrentUserCart() {
        return cartRepo.findByUser(getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto getCartForCurrentUser() {
        ShoppingCart cart = getCurrentUserCart();
        System.out.println("Fetched cart items: " + cart.getCartItems().size());
        System.out.println("First book: " + cart.getCartItems().get(0).getBook());
        System.out.println("First book title: " + cart.getCartItems().get(0).getBook().getTitle());
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartItemResponseDto addItem(CreateCartItemRequestDto dto) {
        ShoppingCart cart = getCurrentUserCart();

        Book book = bookRepo.findById(dto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Optional<CartItem> existing = cart.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst();

        CartItem item = existing.orElseGet(() -> {
            CartItem newItem = cartItemMapper.toEntity(dto);
            newItem.setShoppingCart(cart);
            newItem.setBook(book);
            return newItem;
        });

        item.setQuantity(existing.map(i -> i.getQuantity() + dto.quantity())
                .orElse(dto.quantity()));

        itemRepo.save(item);
        return cartItemMapper.toDto(item);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateItem(Long cartItemId, UpdateCartItemRequestDto dto) {
        CartItem item = itemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

        item.setQuantity(dto.quantity());
        itemRepo.save(item);

        return cartItemMapper.toDto(item);
    }

    @Override
    @Transactional
    public void removeItem(Long cartItemId) {
        CartItem item = itemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));
        itemRepo.delete(item);
    }
}
