package com.jinmlee.articleProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginMemberDto {
    private String loginId;
    private String password;
}
