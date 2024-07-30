package com.jinmlee.articleProject.dto.member;

import com.jinmlee.articleProject.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private Long id;
    private String loginId;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
    }
}
