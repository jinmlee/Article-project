package com.jinmlee.articleProject.queryDsl.article;

import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.queryDsl.article.ArticleRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.jinmlee.articleProject.entity.article.QArticle.article;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Article> findArticleById(Long id) {

        return Optional.ofNullable(queryFactory.select(article).from(article)
                .where(article.id.eq(id).and(article.deletedAt.isNull()))
                .fetchOne());
    }

}
