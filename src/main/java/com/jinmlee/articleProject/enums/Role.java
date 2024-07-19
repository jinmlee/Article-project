package com.jinmlee.articleProject.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}
