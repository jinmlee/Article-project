package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.AddArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article save(AddArticleDto addArticleDto){
        return articleRepository.save(addArticleDto.toEntity());
    }
}
