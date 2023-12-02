package com.hlteam.naturezen.Filter.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryFilter {
    public static final String FIElD_NAME = "name";
    public static final String FiELD_ENABLE ="enabled";
    private String name;
    private Boolean enabled;
}
