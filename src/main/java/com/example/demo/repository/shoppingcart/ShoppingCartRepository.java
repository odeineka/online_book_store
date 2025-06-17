package com.example.demo.repository.shoppingcart;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @EntityGraph(attributePaths = {"user", "cartItems", "cartItems.book"})
    Optional<ShoppingCart> findByUser(User user);
}
