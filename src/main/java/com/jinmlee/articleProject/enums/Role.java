package com.jinmlee.articleProject.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

}
