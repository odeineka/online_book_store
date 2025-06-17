package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.cart.CartItemResponseDto;
import com.example.demo.dto.cart.CreateCartItemRequestDto;
import com.example.demo.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring", uses = {BookMapper.class})
public interface CartItemMapper {
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toEntity(CreateCartItemRequestDto dto);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);
}
