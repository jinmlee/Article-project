package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member save(AddMemberDto addMemberDto){
        return memberRepository.save(addMemberDto.toEntity());
    }

    public Member getMemberByLoginId(String loginId){
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        if(findMember.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"아이디를 확인해 주세요");
        }
        return findMember.get();
    }

    public void verifyPassword(String inputPassword, String getPassword){
        if(!inputPassword.equals(getPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호를 확인해 주세요");
        }
    }
}
