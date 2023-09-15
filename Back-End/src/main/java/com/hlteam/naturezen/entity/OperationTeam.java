package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "operation_team", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
public class OperationTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String phoneNumber;
    private String managerName;
    public OperationTeam(){
        this.name = name;
    }
}