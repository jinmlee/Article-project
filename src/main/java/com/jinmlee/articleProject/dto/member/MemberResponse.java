package com.jinmlee.articleProject.dto.member;

import com.jinmlee.articleProject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String loginId;
    private String name;

    public MemberResponse(Member member){
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
    }
}
