package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class ShoppingCart {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;
}
