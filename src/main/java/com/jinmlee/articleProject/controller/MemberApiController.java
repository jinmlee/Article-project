package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.AddMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<Member> addMember(@Valid @RequestBody AddMemberDto addMemberDto){
        Member savedMember = memberService.save(addMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }
}
