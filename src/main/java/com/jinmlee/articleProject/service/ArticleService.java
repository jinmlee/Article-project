package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article save(AddArticleDto addArticleDto){
        return articleRepository.save(addArticleDto.toEntity());
    }

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Optional<Article> findById(long id){
        return articleRepository.findById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleDto updateArticleDto){
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not fount: " + id));

        article.update(updateArticleDto.getTitle(), updateArticleDto.getContent());

        return article;
    }
}
