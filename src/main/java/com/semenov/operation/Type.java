package com.semenov.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
    SEARCH("search"),
    STAT("stat"),
    ERROR("error");

    private String type;
}
