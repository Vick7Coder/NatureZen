package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String type;

    private long size;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}
