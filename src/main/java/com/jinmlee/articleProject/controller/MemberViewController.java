package com.jinmlee.articleProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    @GetMapping("/member/join")
    public String joinMember(){
        return "member/join";
    }

    @GetMapping("/member/login")
    public String login(){
        return "member/login";
    }
}
