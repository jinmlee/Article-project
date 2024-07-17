package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleViewDto;
import com.jinmlee.articleProject.dto.article.ArticleViewListDto;
import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            model.addAttribute("article", new AddArticleViewDto());
        }else {
            Article article = articleService.getById(id);
            model.addAttribute("article", new AddArticleViewDto(article));
        }

        return "article/newArticle";
    }

    @GetMapping("/articleList")
    public String viewArticleList(Model model){

        List<ArticleViewListDto> articleList = articleService.getList().stream().map(ArticleViewListDto:: new).toList();
        model.addAttribute("articleList", articleList);

        return "article/articleList";
    }

    @GetMapping("/article{id}")
    public String viewArticle(@PathVariable long id, Model model){

        Article article = articleService.getById(id);

        model.addAttribute("article", new ArticleViewDto(article));

        return "article/article";
    }

}
