package com.jinmlee.articleProject.repository.article;

import com.jinmlee.articleProject.entity.article.ArticleLike;
import com.jinmlee.articleProject.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    @Query("select count (al) > 0 from ArticleLike al where al.member = :member and al.article.id = :articleId")
    boolean existsByArticleIdAndMember(@Param("articleId") long articleId, @Param("member") Member member);

    @Modifying
    @Transactional
    @Query("DELETE FROM ArticleLike al WHERE al.article.id = :articleId AND al.member = :member")
    void deleteByArticleIdAndMember(@Param("articleId") Long articleId, @Param("member") Member member);
}
