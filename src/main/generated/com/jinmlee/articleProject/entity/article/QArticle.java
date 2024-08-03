package com.jinmlee.articleProject.entity.article;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 573599569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final com.jinmlee.articleProject.entity.QBaseTimeEntity _super = new com.jinmlee.articleProject.entity.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.Instant> createdDate = _super.createdDate;

    public final DateTimePath<java.time.Instant> deletedAt = createDateTime("deletedAt", java.time.Instant.class);

    public final NumberPath<Long> hits = createNumber("hits", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.jinmlee.articleProject.entity.member.QMember member;

    //inherited
    public final DateTimePath<java.time.Instant> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.jinmlee.articleProject.entity.member.QMember(forProperty("member")) : null;
    }

}

