package com.jinmlee.articleProject.entity.alarm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEditLimitedLineAlarm is a Querydsl query type for EditLimitedLineAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEditLimitedLineAlarm extends EntityPathBase<EditLimitedLineAlarm> {

    private static final long serialVersionUID = 1335792311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEditLimitedLineAlarm editLimitedLineAlarm = new QEditLimitedLineAlarm("editLimitedLineAlarm");

    public final QAlarm _super;

    public final com.jinmlee.articleProject.entity.article.QArticle article;

    //inherited
    public final DateTimePath<java.time.Instant> createdDate;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final com.jinmlee.articleProject.entity.member.QMember member;

    //inherited
    public final StringPath message;

    //inherited
    public final BooleanPath readFlag;

    public QEditLimitedLineAlarm(String variable) {
        this(EditLimitedLineAlarm.class, forVariable(variable), INITS);
    }

    public QEditLimitedLineAlarm(Path<? extends EditLimitedLineAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEditLimitedLineAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEditLimitedLineAlarm(PathMetadata metadata, PathInits inits) {
        this(EditLimitedLineAlarm.class, metadata, inits);
    }

    public QEditLimitedLineAlarm(Class<? extends EditLimitedLineAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAlarm(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.jinmlee.articleProject.entity.article.QArticle(forProperty("article"), inits.get("article")) : null;
        this.createdDate = _super.createdDate;
        this.id = _super.id;
        this.member = _super.member;
        this.message = _super.message;
        this.readFlag = _super.readFlag;
    }

}

