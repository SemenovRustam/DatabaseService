package com.semenov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerStatistic {
    private String name;
    private List<Purchase> purchases;
    private Integer totalExpenses;

}
