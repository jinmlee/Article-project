package com.jinmlee.articleProject.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberInfo is a Querydsl query type for MemberInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInfo extends EntityPathBase<MemberInfo> {

    private static final long serialVersionUID = -1069832945L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberInfo memberInfo = new QMemberInfo("memberInfo");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QMemberInfo(String variable) {
        this(MemberInfo.class, forVariable(variable), INITS);
    }

    public QMemberInfo(Path<? extends MemberInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberInfo(PathMetadata metadata, PathInits inits) {
        this(MemberInfo.class, metadata, inits);
    }

    public QMemberInfo(Class<? extends MemberInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

