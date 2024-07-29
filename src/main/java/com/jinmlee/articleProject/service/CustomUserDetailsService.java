package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        if (findMember.isPresent()) {
            return new CustomUserDetails(findMember.get());
        }

        throw new UsernameNotFoundException("아이디를 확인해 주세요");
    }
}
