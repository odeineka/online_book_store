package com.example.demo.mapper;

import com.example.demo.config.MapperConfig;
import com.example.demo.dto.cart.CartItemResponseDto;
import com.example.demo.dto.cart.ShoppingCartResponseDto;
import com.example.demo.model.CartItem;
import com.example.demo.model.ShoppingCart;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class,componentModel = "spring", uses = {CartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "mapCartItems")
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Named("mapCartItems")
    default List<CartItemResponseDto> mapCartItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::mapCartItemToDto)
                .collect(Collectors.toList());
    }

    CartItemResponseDto mapCartItemToDto(CartItem cartItem);
}

