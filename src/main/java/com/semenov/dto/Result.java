package com.semenov.dto;

import com.semenov.criterias.Criteria;
import com.semenov.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private Criteria criteria;
    private List<Customer> results;
}
