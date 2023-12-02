package com.hlteam.naturezen.Filter.Tour;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourFilter {
    public static final String FIELD_ABSTR = "abstr";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_ACTIVE = "active";

    private String abstr;
    private Long category;
    private Boolean active;


}
