package com.hlteam.naturezen.service.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingRequest {
    private Integer page;

    private Integer limit;

    private Sorter sorter;
}
