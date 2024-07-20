package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Member save(AddMemberDto addMemberDto){
        return memberRepository.save(Member.builder()
                        .name(addMemberDto.getName())
                        .loginId(addMemberDto.getLoginId())
                        .password(bCryptPasswordEncoder.encode(addMemberDto.getPassword()))
                        .email(addMemberDto.getEmail())
                        .phoneNumber(addMemberDto.getPhoneNumber())
                        .role(Role.USER)
                        .build());
    }
}
