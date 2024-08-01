package com.jinmlee.articleProject.controller.article;

import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.service.article.ArticleLikeService;
import com.jinmlee.articleProject.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleService articleService;
    private final ArticleLikeService articleLikeService;

    @PostMapping("/api/article/{id}/like")
    public ResponseEntity<String> addLike(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        if(articleLikeService.existsLike(id, customUserDetails.getMember())){
            articleLikeService.deleteLike(id, customUserDetails.getMember());
        }else {
            articleLikeService.addLike(id, customUserDetails.getMember());
        }

        return ResponseEntity.ok().body("");
    }
}
