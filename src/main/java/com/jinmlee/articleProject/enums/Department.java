package com.jinmlee.articleProject.enums;

import lombok.Getter;

@Getter
public enum Department {
    BACKEND("백엔드"),
    FRONTEND("프론트"),
    DESIGN("디자인");

    private final String departmentName;

    Department(String departmentName){
        this.departmentName = departmentName;
    }
}
