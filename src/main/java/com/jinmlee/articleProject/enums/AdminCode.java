package com.jinmlee.articleProject.enums;

import lombok.Getter;

@Getter
public enum AdminCode {
    CODE("admin_code");

    private final String code;
    AdminCode(String code) {
        this.code = code;
    }
}
