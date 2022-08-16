package com.semenov.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResult {
    private String type;
    private List<Result> results;
}
