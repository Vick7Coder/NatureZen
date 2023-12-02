package com.hlteam.naturezen.dto.request;

import com.hlteam.naturezen.entity.Category;
import com.hlteam.naturezen.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
    private String title;
    private String abstr;
    private String desciption;
    private String image;
    private Category category;
    private boolean isActive;
    private int totalSlot;
    private LocalDateTime beginDate;
    private LocalDateTime finishDate;
    private LocalDateTime closeDate;
    private double price;
    private Set<Image> images = new HashSet<>();
}
