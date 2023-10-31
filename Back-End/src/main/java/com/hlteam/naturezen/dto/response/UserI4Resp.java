package com.hlteam.naturezen.dto.response;

import com.hlteam.naturezen.entity.Cart;
import com.hlteam.naturezen.entity.Order;
import com.hlteam.naturezen.entity.Role;
import com.hlteam.naturezen.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserI4Resp {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean gender;
    private LocalDateTime birthDay;
    private Cart cart;
    private List<Order> order;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
}
