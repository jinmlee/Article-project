package com.jinmlee.articleProject.dto.comment;

import com.jinmlee.articleProject.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private long id;
    private String content;
    private long articleId;
    private long memberId;

    public CommentResponse(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.articleId = comment.getArticle().getId();
        this.memberId = comment.getMember().getId();
    }
}
