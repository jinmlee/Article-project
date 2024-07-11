package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.AddMemberDto;
import com.jinmlee.articleProject.dto.LoginMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member save(AddMemberDto addMemberDto){
        return memberRepository.save(addMemberDto.toEntity());
    }

    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }

    public boolean verifyPassword(String inputPassword, String getPassword){
        return inputPassword.equals(getPassword);
    }
}
