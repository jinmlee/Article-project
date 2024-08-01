package com.jinmlee.articleProject.repository.article;

import com.jinmlee.articleProject.dto.article.ArticleViewDto;
import com.jinmlee.articleProject.dto.article.ArticleViewListDto;
import com.jinmlee.articleProject.entity.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where a.id = :id and a.deletedAt is null")
    Optional<Article> findById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Article a set a.hits = a.hits + :hits where a.id = :articleId")
    void updateHits(@Param("articleId") Long articleId, @Param("hits") Long hits);

    @Query("select new com.jinmlee.articleProject.dto.article.ArticleViewListDto(a.id, a.title, mi.name, a.hits, a.createdDate) " +
            "from Article a " +
            "left join a.member m " +
            "left join MemberInfo mi on mi.member = m " +
            "where a.deletedAt is null and a.title like %:keyword%")
    Page<ArticleViewListDto> getArticleSortedList(@Param("keyword") String keyword, Pageable pageable);

    @Query("select new com.jinmlee.articleProject.dto.article.ArticleViewDto(a.id, a.title, a.content, m.id, mi.name, a.hits, a.createdDate) " +
            "from Article a " +
            "left join a.member m " +
            "left join MemberInfo mi on mi.member = m " +
            "where a.id = :id and a.deletedAt is null")
    ArticleViewDto findViewArticle(@Param("id") long id);

    @Query("select a from Article a where a.createdDate BETWEEN :start and :end")
    List<Article> findEditLimitedLineArticle(@Param("start") Instant start, @Param("end") Instant end);
}
