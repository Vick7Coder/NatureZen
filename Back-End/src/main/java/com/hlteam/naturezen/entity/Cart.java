package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalItem;
    private double totalPrice;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItems;
    public Cart(){
        this.cartItems = new HashSet<>();
        this.totalItem = 0;
        this.totalPrice = 0.0;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalItem=" + totalItem +
                ", totalPrice=" + totalPrice +
                ", user=" + user.getUsername() +
                ", cartItems=" + cartItems.size() +
                '}';
    }
}
