package com.semenov.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class CustomerStatistic {
    private String name;
    private List<Purchase> purchases;
    private Integer totalExpenses;

}
