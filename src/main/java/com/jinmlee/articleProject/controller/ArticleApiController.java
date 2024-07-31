package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.ArticleResponse;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody AddArticleDto addArticleDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Article savedArticle = articleService.save(addArticleDto, customUserDetails.getMember());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ArticleResponse(savedArticle));
    }

    @GetMapping("/api/articles")
    public ResponseEntity<ArticlePageDto> findAllArticle(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "CREATED_DESC") ArticleSortType sortType) {

        return ResponseEntity.ok().body(articleService.getList(page, sortType));
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {

        Article findArticle = articleService.getById(id);

        return ResponseEntity.ok().body(new ArticleResponse(findArticle));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable long id, @RequestBody UpdateArticleDto updateArticleDto) {

        Article updateArticle = articleService.update(id, updateArticleDto);

        return ResponseEntity.ok().body(new ArticleResponse(updateArticle));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable long id){

        articleService.softDelete(id);

        return ResponseEntity.ok().body("삭제가 완료 되었습니다.");
    }

    @GetMapping("/api/articles/{id}/editable")
    public ResponseEntity<ArticleResponse> checkEditable(@PathVariable long id) {

        Article article = articleService.getById(id);

        if (!article.isEditable()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok().body(new ArticleResponse(article));
    }
}
