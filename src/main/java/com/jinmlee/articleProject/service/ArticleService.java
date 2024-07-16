package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public Article save(AddArticleDto addArticleDto, long loggedId){

        Member loggedMember = memberRepository.findById(loggedId).orElseThrow(() -> new IllegalArgumentException("not found member: " + loggedId));

        return articleRepository.save(Article.builder()
                .title(addArticleDto.getTitle())
                .content(addArticleDto.getContent())
                .member(loggedMember).build());
    }

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article findById(long id){
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found article: " + id));
    }

    @Transactional
    public Article update(long id, UpdateArticleDto updateArticleDto){

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(updateArticleDto.getTitle(), updateArticleDto.getContent());

        return article;
    }

    public void isEditable(Article article){

        Instant timeNow = Instant.now();

        if(ChronoUnit.DAYS.between(article.getCreatedDate(), timeNow) > 10) {
            throw new IllegalArgumentException("수정 가능 기한이 지났습니다.");
        }
    }
}
