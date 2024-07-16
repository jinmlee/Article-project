package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member save(AddMemberDto addMemberDto){
        return memberRepository.save(addMemberDto.toEntity());
    }

    public Member findByLoginId(String loginId){
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        if(findMember.isEmpty()){
            throw new IllegalArgumentException("아이디를 확인해 주세요");
        }
        return findMember.get();
    }

    public void verifyPassword(String inputPassword, String getPassword){
        if(!inputPassword.equals(getPassword)){
            throw new IllegalArgumentException("비밀번호를 확인해 주세요");
        }
    }
}
