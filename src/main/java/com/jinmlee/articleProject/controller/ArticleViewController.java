package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleViewDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.ArticleViewListDto;
import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String newArticle(@RequestParam(required = false) Long id, Model model){

        if(id == null){
            model.addAttribute("article", new AddArticleViewDto());
        }else {
            Article article = articleService.getById(id);
            model.addAttribute("article", new AddArticleViewDto(article));
        }

        return "article/newArticle";
    }

    @GetMapping("/articleList")
    public String viewArticleList(Model model, @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "CREATED_DESC") ArticleSortType sortType){

        ArticlePageDto findArticleList = articleService.getList(page, sortType);

        model.addAttribute("pageDto", findArticleList);

        return "article/articleList";
    }

    @GetMapping("/article{id}")
    public String viewArticle(@PathVariable long id, Model model){

        Article article = articleService.getById(id);

        model.addAttribute("article", new ArticleViewDto(article));

        return "article/article";
    }

}
