package com.jinmlee.articleProject.dto.comment;

import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDto {

    private String content;

    public Comment toEntity(Article article, Member member){
        return Comment.builder()
                .content(content)
                .article(article)
                .member(member)
                .build();
    }
}
