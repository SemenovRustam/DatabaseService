package com.semenov.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNameCriteria implements Criteria {

    private String productName;

    private  Integer minTimes;

}
