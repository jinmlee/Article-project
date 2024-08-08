package com.jinmlee.articleProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    @GetMapping("/member/join")
    public String joinMember() {
        return "member/join";
    }

    @GetMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/admin")
    public String admin(){
        return "member/admin";
    }

    @GetMapping("/manager")
    public String manager(){
        return "member/manager";
    }

    @GetMapping("/user")
    public String user(){
        return "member/user";
    }
}
