package com.jinmlee.articleProject.repository.article;

import com.jinmlee.articleProject.entity.article.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    @Query("select count (al) > 0 from ArticleLike al where al.memberId = :memberId and al.articleId = :articleId")
    boolean existsByArticleIdAndMember(@Param("articleId") long articleId, @Param("memberId") long memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ArticleLike al WHERE al.articleId = :articleId AND al.memberId = :member")
    void deleteByArticleIdAndMember(@Param("articleId") Long articleId, @Param("member") long memberId);
}
