package com.jinmlee.articleProject.entity.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleLike is a Querydsl query type for ArticleLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleLike extends EntityPathBase<ArticleLike> {

    private static final long serialVersionUID = -1426423160L;

    public static final QArticleLike articleLike = new QArticleLike("articleLike");

    public final NumberPath<Long> articleId = createNumber("articleId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QArticleLike(String variable) {
        super(ArticleLike.class, forVariable(variable));
    }

    public QArticleLike(Path<? extends ArticleLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleLike(PathMetadata metadata) {
        super(ArticleLike.class, metadata);
    }

}

