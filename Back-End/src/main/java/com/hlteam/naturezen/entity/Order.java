package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private String orderStatus;
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private int tax;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;
    private int quantity;
    private double totalPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", orderStatus='" + orderStatus + '\'' +
                ", amount=" + amount +
                ", user=" + user.getUsername() +
                ", tax=" + tax +
                ", payment=" + payment +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderDetailList=" + orderDetailList.size() +
                '}';
    }
}
