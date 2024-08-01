package com.jinmlee.articleProject.service.article;

import com.jinmlee.articleProject.dto.article.*;
import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.handler.ResourceNotFoundException;
import com.jinmlee.articleProject.repository.article.ArticleRepository;
import com.jinmlee.articleProject.util.PageCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Article save(AddArticleDto addArticleDto, Member member) {

        return articleRepository.save(Article.builder()
                .title(addArticleDto.getTitle())
                .content(addArticleDto.getContent())
                .member(member)
                .build());
    }

    public ArticlePageDto getList(int page, ArticleSortType sortType, String keyword) {

        ArticlePageDto pageDto = new ArticlePageDto();

        page = PageCalculator.calculateValidPageNumber(page, articleRepository.count(), pageDto.getPageSize());
        Sort sort = Sort.by(Sort.Order.by(sortType.getField()).with(Sort.Direction.fromString(sortType.getDirection())));
        Pageable pageable = PageRequest.of(page - 1, pageDto.getPageSize(), sort);

        Page<ArticleViewListDto> sortedArticle = articleRepository.getArticleSortedList(keyword, pageable);

        return pageDto.updateDto(sortedArticle);
    }

    public Article getById(long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found article: " + id));
    }

    public ArticleViewDto getViewArticle(long id){

        ArticleViewDto articleViewDto = articleRepository.findViewArticle(id);

        articleViewDto.setModifyLimitedDate();

        return articleViewDto;
    }

    public void incrementViewCount(long articleId, long memberId) {
        String key = "article:hits:" + articleId + ":" + memberId;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().increment(key);
        }
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void updateViewCount() {
        Set<String> keys = redisTemplate.keys("article:hits:*:*");

        if (keys == null) {
            return;
        }

        Map<Long, Long> articleHits = new HashMap<>();
        for (String key : keys) {
            Long articleId = Long.parseLong(key.split(":")[2]);
            articleHits.put(articleId, articleHits.getOrDefault(articleId, 0L) + 1);
        }

        for (Map.Entry<Long, Long> entry : articleHits.entrySet()) {
            articleRepository.updateHits(entry.getKey(), entry.getValue());
        }

        redisTemplate.delete(keys);
    }

    @Transactional
    public void softDelete(long id){

        Optional<Article> findArticle = articleRepository.findById(id);
        if(findArticle.isEmpty()){
            throw new ResourceNotFoundException("Article not found with id: " + id);
        }

        Article article = findArticle.get();
        article.delete();
    }


    @Transactional
    public Article update(long id, UpdateArticleDto updateArticleDto) {

        Optional<Article> findArticle = articleRepository.findById(id);
        if(findArticle.isEmpty()){
            throw new ResourceNotFoundException("Article not found with id: " + id);
        }

        Article article = findArticle.get();
        article.update(updateArticleDto.getTitle(), updateArticleDto.getContent());

        return article;
    }
}
