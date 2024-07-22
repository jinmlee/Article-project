package com.jinmlee.articleProject.controller;

import com.jinmlee.articleProject.dto.comment.AddCommentDto;
import com.jinmlee.articleProject.dto.comment.CommentResponse;
import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.comment.Comment;
import com.jinmlee.articleProject.service.ArticleService;
import com.jinmlee.articleProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/api/article/{articleId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable long articleId, @RequestBody AddCommentDto addCommentDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Article article = articleService.getById(articleId);

        Comment savedComment = commentService.save(addCommentDto, article, customUserDetails.getMember());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse(savedComment));
    }

    @DeleteMapping("/api/article/{articleId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long articleId, @PathVariable long commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        commentService.isAuthor(customUserDetails.getMember(), commentId);

        commentService.delete(commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 완료되었습니다.");
    }
}
