package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Article save(AddArticleDto addArticleDto, Member member){

        return articleRepository.save(Article.builder()
                .title(addArticleDto.getTitle())
                .content(addArticleDto.getContent())
                .member(member).build());
    }

    public Page<Article> getList(int page, ArticleSortType sortType, ArticlePageDto pageDto){

        page = isValidPage(page, pageDto.getPageSize());

        Sort sort = Sort.by(Sort.Order.by(sortType.getField()).with(Sort.Direction.fromString(sortType.getDirection())));
        Pageable pageable = PageRequest.of(page - 1, pageDto.getPageSize(), sort);

        Page<Article> sortedArticle = articleRepository.findAll(pageable);
        pageDto.updateDto(sortedArticle);

        return sortedArticle;
    }

    public int isValidPage(int page, int pageSize){
        long totalArticles = articleRepository.count();
        int totalPages = (int)((totalArticles + pageSize - 1) / pageSize);
        if(page > totalPages){
            return totalPages;
        } else if (page < 1) {
            return 1;
        }
        return page;
    }

    public Article getById(long id){
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found article: " + id));
    }

    public void incrementViewCount(long articleId, long memberId){
        String key = "article:hits:" + articleId + ":" + memberId;

        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            redisTemplate.opsForValue().increment(key);
        }
    }
    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void updateViewCount(){
        Set<String> keys = redisTemplate.keys("article:hits:*:*");

        if(keys == null){
            return;
        }

        Map<Long, Long> articleHits = new HashMap<>();
        for(String key : keys){
            Long articleId = Long.parseLong(key.split(":")[2]);
            articleHits.put(articleId, articleHits.getOrDefault( articleId, 0L) + 1);
        }

        for (Map.Entry<Long, Long> entry : articleHits.entrySet()){
            articleRepository.updateHits(entry.getKey(), entry.getValue());
        }

        redisTemplate.delete(keys);
    }


    @Transactional
    public Article update(long id, UpdateArticleDto updateArticleDto){

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(updateArticleDto.getTitle(), updateArticleDto.getContent());

        return article;
    }
}
