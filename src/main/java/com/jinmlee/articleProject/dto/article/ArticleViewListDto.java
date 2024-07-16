package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewListDto {

    private long id;
    private String title;
    private String memberName;
    private Instant createdDate;

    public ArticleViewListDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.memberName = article.getMember().getName();
        this.createdDate = article.getCreatedDate();
    }
}
