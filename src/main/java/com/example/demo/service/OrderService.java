package com.example.demo.service;

import com.example.demo.dto.order.CreateOrderRequestDto;
import com.example.demo.dto.order.OrderItemWithoutPriceResponseDto;
import com.example.demo.dto.order.OrderResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto placeOrder(CreateOrderRequestDto requestDto);

    Page<OrderResponseDto> getOrderHistory(Pageable pageable);

    OrderResponseDto updateStatus(Long orderId, String status);

    List<OrderItemWithoutPriceResponseDto> getAllItemsByOrderId(Long orderId);

    OrderItemWithoutPriceResponseDto getOrderItemById(Long orderId, Long itemId);
}
