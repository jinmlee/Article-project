package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.member.MemberInfo;
import com.jinmlee.articleProject.enums.AdminCode;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.MemberInfoRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member save(AddMemberDto addMemberDto) {
        // TODO add validation

        if(addMemberDto.getAdminCode() != null){
            isValidAdminCode(addMemberDto);
        }else {
            addMemberDto.setRole(Role.USER);
        }

        Member member = memberRepository.save(addMemberDto.toEntityMember(bCryptPasswordEncoder));

        if(member.getRole() == Role.ADMIN){
            return member;
        }

        MemberInfo memberInfo = memberInfoRepository.save(addMemberDto.toEntityMemberInfo(member));

        return member;
    }

    public void isValidAdminCode(AddMemberDto addMemberDto){
        if (AdminCode.CODE.getCode().equals(addMemberDto.getAdminCode())){
            addMemberDto.setRole(Role.ADMIN);
        }else {
            throw new IllegalArgumentException("관리자 번호가 일치하지 않습니다.");
        }
    }
}
