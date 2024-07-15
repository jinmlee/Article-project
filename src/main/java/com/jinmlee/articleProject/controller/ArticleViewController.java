package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleViewController {

    private final ArticleService articleService;

    @GetMapping("/newArticle")
    public String newArticle(@RequestParam(required = false) Long id, Model model, HttpSession httpSession){
        SessionMemberDto loggedMember = (SessionMemberDto) httpSession.getAttribute("loggedMember");
        if(loggedMember == null){
            return "redirect:/member/login";
        }
        if(id == null){
            model.addAttribute("article", new ArticleViewDto());
        }else {
            Article article = articleService.findById(id);
            model.addAttribute("article", new ArticleViewDto(article, loggedMember));
        }
        return "article/newArticle";
    }

}
