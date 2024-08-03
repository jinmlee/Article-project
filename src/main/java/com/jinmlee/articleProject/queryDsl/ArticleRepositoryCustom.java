package com.jinmlee.articleProject.queryDsl;

import com.jinmlee.articleProject.entity.article.Article;

import java.util.Optional;

public interface ArticleRepositoryCustom {
    Optional<Article> findArticleById(Long id);
}
