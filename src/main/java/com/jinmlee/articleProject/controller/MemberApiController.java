package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.member.AddMemberDto;
import com.jinmlee.articleProject.dto.member.LoginMemberDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<?> addMember(@Valid @RequestBody AddMemberDto addMemberDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Member savedMember = memberService.save(addMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @PostMapping("/api/members/login")
    public ResponseEntity<?> login(@RequestBody LoginMemberDto loginMemberDto, HttpSession session){
        Member findMember = memberService.findByLoginId(loginMemberDto.getLoginId());
        if(findMember == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디를 확인해 주세요");
        }

        if(!memberService.verifyPassword(loginMemberDto.getPassword(), findMember.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호를 확인해주세요");
        }

        SessionMemberDto loggedMember = new SessionMemberDto(findMember.getId(), findMember.getName());

        session.setAttribute("loggedMember", loggedMember);
        return ResponseEntity.ok().body(findMember);
    }
}
