package com.jinmlee.articleProject.repository;

import com.jinmlee.articleProject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query("update Article a set a.hits = a.hits + :hits where a.id = :articleId")
    void updateHits(@Param("articleId") Long articleId, @Param("hits") Long hits);
}
