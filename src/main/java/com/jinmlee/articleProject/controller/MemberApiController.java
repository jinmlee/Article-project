package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.dto.member.LoginMemberDto;
import com.jinmlee.articleProject.dto.member.MemberResponse;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<MemberResponse> addMember(@Valid @RequestBody AddMemberDto addMemberDto){

        Member savedMember = memberService.save(addMemberDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberResponse(savedMember));
    }

    @PostMapping("/api/members/logout")
    public ResponseEntity<String> logout(HttpSession session){

        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("로그아웃 하였습니다.");
    }
}
