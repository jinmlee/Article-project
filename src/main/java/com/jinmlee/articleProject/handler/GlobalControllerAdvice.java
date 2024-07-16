package com.jinmlee.articleProject.handler;

import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void loginSessionAddAttributes(Model model, HttpSession httpSession){

        SessionMemberDto loggedMember = (SessionMemberDto) httpSession.getAttribute("loggedMember");
        if(loggedMember != null){
            model.addAttribute("loggedMember", loggedMember);
        }
    }
}
