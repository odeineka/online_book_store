package com.example.demo.service.impl;

import com.example.demo.dto.order.CreateOrderRequestDto;
import com.example.demo.dto.order.OrderItemWithoutPriceResponseDto;
import com.example.demo.dto.order.OrderResponseDto;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.Status;
import com.example.demo.model.User;
import com.example.demo.repository.cartitem.CartItemRepository;
import com.example.demo.repository.order.OrderItemRepository;
import com.example.demo.repository.order.OrderRepository;
import com.example.demo.repository.shoppingcart.ShoppingCartRepository;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository cartRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto placeOrder(CreateOrderRequestDto requestDto) {
        User currentUser = getCurrentUser();
        ShoppingCart cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user: " + currentUser.getId()));
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cannot place an order with an empty cart " + cart);
        }
        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    return orderItem;
                }).collect(Collectors.toSet());
        BigDecimal total = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderResponseDto> getOrderHistory(Pageable pageable) {
        User user = getCurrentUser();
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        return orders.map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDto updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Order with ID %d not found", orderId)));
        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemWithoutPriceResponseDto> getAllItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return order.getOrderItems().stream()
                .map(item -> new OrderItemWithoutPriceResponseDto(
                        item.getId(),
                        item.getBook().getId(),
                        item.getQuantity()))
                .toList();
    }

    @Override
    public OrderItemWithoutPriceResponseDto getOrderItemById(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Order %d not found", orderId)));

        return order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .map(item -> new OrderItemWithoutPriceResponseDto(
                        item.getId(),
                        item.getBook().getId(),
                        item.getQuantity()))
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Item with ID %d not found in this order %d", itemId, orderId)));
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
