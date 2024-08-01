package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.entity.article.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleViewDto {

    private Long id;
    private String title;
    private String content;

    public AddArticleViewDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
