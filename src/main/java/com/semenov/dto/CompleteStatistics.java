package com.semenov.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CompleteStatistics {
    private String type;
    private Integer totalDays;
    private List<CustomerStatistic> customers;
    private Integer totalExpenses;
    private BigDecimal avgExpenses;
}
