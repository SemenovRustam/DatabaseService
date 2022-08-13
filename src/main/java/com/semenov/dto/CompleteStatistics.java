package com.semenov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CompleteStatistics {

    private String type;

    private Integer totalDays;

    private List<CustomerStatistic> customers;

    private Integer totalExpenses;

    private BigDecimal avgExpenses;
}
