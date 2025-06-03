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

@Transactional
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getCartForCurrentUser() {
        ShoppingCart cart = getCurrentUserCart();
        return shoppingCartMapper.toDto(cart);
    }

    @Override
    public CartItemResponseDto addItem(CreateCartItemRequestDto dto) {
        ShoppingCart cart = getCurrentUserCart();

        Book book = bookRepo.findById(dto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Optional<CartItem> existing = itemRepo.findByShoppingCartAndBook(cart, book);

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
    public CartItemResponseDto updateItem(Long cartItemId, UpdateCartItemRequestDto dto) {
        CartItem item = itemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

        item.setQuantity(dto.quantity());
        itemRepo.save(item);

        return cartItemMapper.toDto(item);
    }

    @Override
    public void removeItem(Long cartItemId) {
        CartItem item = itemRepo.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));
        User currentUser = getCurrentUser();
        itemRepo.delete(item);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) auth.getPrincipal()).getUser();
    }

    private ShoppingCart getCurrentUserCart() {
        return cartRepo.findByUser(getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    public ShoppingCart createCartForUser(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return cartRepo.save(cart);
    }
}
