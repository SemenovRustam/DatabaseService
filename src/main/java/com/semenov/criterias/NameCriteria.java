package com.semenov.criterias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameCriteria implements Criteria {
    private String lastName;
}
