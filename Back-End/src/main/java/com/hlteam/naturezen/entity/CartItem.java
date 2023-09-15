package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "cart_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double unitPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", tour=" + tour.getTitle() +
                ", cart=" + cart.getId() +
                '}';
    }
}
