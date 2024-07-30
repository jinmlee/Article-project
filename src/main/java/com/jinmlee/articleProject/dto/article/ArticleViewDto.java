package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewDto {
    private Long id;
    private String title;
    private String content;
    private long memberId;
    private Instant createdDate;
    private long modifyLimitedDate;

    public ArticleViewDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.memberId = article.getMember().getId();
        this.createdDate = article.getCreatedDate();
        this.modifyLimitedDate = article.getModifyLimitedDate();
    }
}
