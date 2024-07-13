package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.ArticleResponse;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleDto addArticleDto){
        Article savedArticle = articleService.save(addArticleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle(){
        List<ArticleResponse> findArticleList = articleService.findAll().stream()
                .map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(findArticleList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Optional<Article> findArticle = articleService.findById(id);
        if(findArticle.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(new ArticleResponse(findArticle.get()));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleDto updateArticleDto){
        Article updateArticle = articleService.update(id, updateArticleDto);

        return ResponseEntity.ok().body(updateArticle);
    }

    @GetMapping("/api/articles/{id}/editable")
    public ResponseEntity<?> checkEditable(@PathVariable long id){
        if(!articleService.isEditable(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성 이후 10일이 경과하였습니다.");
        }
        return ResponseEntity.ok().body(id + " 수정 가능한 게시글입니다.");
    }
}
