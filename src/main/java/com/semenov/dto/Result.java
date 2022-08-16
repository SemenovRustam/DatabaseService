package com.semenov.dto;

import com.semenov.criterias.Criteria;
import com.semenov.entity.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Result {
    private Criteria criteria;
    private List<Customer> results;
}
