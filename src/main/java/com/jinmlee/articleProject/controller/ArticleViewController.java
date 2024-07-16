package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.ArticleListDto;
import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            Article article = articleService.getById(id);
            model.addAttribute("article", new ArticleViewDto(article));
        }

        return "article/newArticle";
    }

    @GetMapping("/articleList")
    public String viewArticleList(Model model){

        List<ArticleListDto> articleList = articleService.getList().stream().map(ArticleListDto:: new).toList();
        model.addAttribute("articleList", articleList);

        return "article/articleList";
    }

}
