package com.hlteam.naturezen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour", uniqueConstraints = @UniqueConstraint(columnNames = {"title","image"}))
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String abstr;
    @Column(columnDefinition = "TEXT")
    private String desciption;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    private boolean isActive;
    private int totalSlot;
    private LocalDateTime beginDate;
    private LocalDateTime finishDate;
    private LocalDateTime closeDate;
    private int currentSlot;
    private int subSlot;
    private double price;
    @ManyToMany
    @JoinTable(name = "abs_image",joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name="image_id"))
    private Set<Image> images = new HashSet<>();
}
