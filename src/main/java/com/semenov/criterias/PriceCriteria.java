package com.semenov.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCriteria implements Criteria {

    private Integer minExpenses;

    private Integer maxExpenses;

}
