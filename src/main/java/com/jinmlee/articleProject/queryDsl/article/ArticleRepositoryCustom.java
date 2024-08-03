package com.jinmlee.articleProject.queryDsl.article;

import com.jinmlee.articleProject.entity.article.Article;

import java.util.Optional;

public interface ArticleRepositoryCustom {
    Optional<Article> findArticleById(Long id);
}
