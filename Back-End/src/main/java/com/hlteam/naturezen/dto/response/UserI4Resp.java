package com.hlteam.naturezen.dto.response;

import com.hlteam.naturezen.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserI4Resp {
    private User user;
}
