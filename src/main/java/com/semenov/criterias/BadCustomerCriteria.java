package com.semenov.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadCustomerCriteria implements Criteria {
    private Integer badCustomer;
}
