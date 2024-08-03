package com.jinmlee.articleProject.queryDsl.article;

import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.member.QMemberInfo;
import com.jinmlee.articleProject.queryDsl.article.ArticleRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.jinmlee.articleProject.entity.article.QArticle.article;
import static com.jinmlee.articleProject.entity.article.QArticleLike.articleLike;
import static com.jinmlee.articleProject.entity.member.QMember.member;
import static com.jinmlee.articleProject.entity.member.QMemberInfo.memberInfo;

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

    @Override
    public Optional<ArticleViewDto> findViewArticle(Long id) {

        return Optional.ofNullable(queryFactory.select(Projections.constructor(ArticleViewDto.class,
                        article.id,
                        article.title,
                        article.content,
                        memberInfo.member.id,
                        memberInfo.name,
                        article.hits,
                        article.createdDate,
                        articleLike.id.count().as("likes")))
                .from(article)
                .leftJoin(memberInfo).on(memberInfo.member.eq(article.member))
                .leftJoin(articleLike).on(articleLike.articleId.eq(article.id))
                .where(article.id.eq(id))
                .groupBy(article.id)
                .fetchOne());
    }

}
