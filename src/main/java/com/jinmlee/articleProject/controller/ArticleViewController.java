package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleViewDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleViewController {

    private final ArticleService articleService;

    @GetMapping("/newArticle")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        if (id == null) {
            model.addAttribute("article", new AddArticleViewDto());
        } else {
            Article article = articleService.getById(id);
            model.addAttribute("article", new AddArticleViewDto(article));
        }

        return "article/newArticle";
    }

    @GetMapping("/articleList")
    public String viewArticleList(Model model, @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "CREATED_DESC") ArticleSortType sortType,
                                  @RequestParam(defaultValue = "") String keyword) {

        ArticlePageDto findArticleList = articleService.getList(page, sortType, keyword);

        model.addAttribute("pageDto", findArticleList);

        return "article/articleList";
    }

    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ArticleViewDto article = articleService.getViewArticle(id);

        articleService.incrementViewCount(id, customUserDetails.getMember().getId());

        model.addAttribute("article", article);

        return "article/article";
    }

}
