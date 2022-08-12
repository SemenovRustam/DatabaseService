package com.semenov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
public class Sale {

    private Customer customer;

    private Product product;

    private LocalDate date;


}
