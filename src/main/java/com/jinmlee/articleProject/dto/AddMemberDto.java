package com.jinmlee.articleProject.dto;

import com.jinmlee.articleProject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberDto {
    private String name;
    private String loginId;
    private String password;
    private String phoneNumber;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
