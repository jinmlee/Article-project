package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleViewDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.ArticleViewListDto;
import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleViewController {

    private final ArticleService articleService;

    @GetMapping("/newArticle")
    public String newArticle(@RequestParam(required = false) Long id, Model model, HttpSession httpSession){

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
                                  @RequestParam(defaultValue = "CREATED_DESC") ArticleSortType articleSortType){

        ArticlePageDto pageDto = new ArticlePageDto();

        List<ArticleViewListDto> articleList = articleService.getList(page, articleSortType, pageDto).stream().map(ArticleViewListDto:: new).toList();

        model.addAttribute("articleList", articleList);
        model.addAttribute("pageDto", pageDto);


        return "article/articleList";
    }

    @GetMapping("/article{id}")
    public String viewArticle(@PathVariable long id, Model model){

        Article article = articleService.getById(id);

        model.addAttribute("article", new ArticleViewDto(article));

        return "article/article";
    }

}
