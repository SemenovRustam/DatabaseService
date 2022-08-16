package com.semenov.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Purchase {
    private String name;
    private Integer expenses;
}
