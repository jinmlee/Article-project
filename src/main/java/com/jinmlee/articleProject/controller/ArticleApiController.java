package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.AddArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleDto addArticleDto){
        Article savedArticle = articleService.save(addArticleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }
}
