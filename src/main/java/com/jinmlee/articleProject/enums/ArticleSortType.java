package com.jinmlee.articleProject.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ArticleSortType {
    CREATED_ASC("createdDate", "ASC"),
    CREATED_DESC("createdDate", "DESC");

    private final String field;
    private final String direction;

    ArticleSortType(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }
}
