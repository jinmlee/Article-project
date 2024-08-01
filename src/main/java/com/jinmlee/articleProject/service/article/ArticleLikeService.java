package com.jinmlee.articleProject.service.article;

import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.article.ArticleLike;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.handler.ResourceNotFoundException;
import com.jinmlee.articleProject.repository.article.ArticleLikeRepository;
import com.jinmlee.articleProject.repository.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    public boolean existsLike(long articleId, Member member){
        return articleLikeRepository.existsByArticleIdAndMember(articleId, member);
    }

    public void addLike(long articleId, Member member){

        Optional<Article> findArticle = articleRepository.findById(articleId);

        if(findArticle.isEmpty()){
            throw new ResourceNotFoundException("Not found article : " + articleId);
        }

        Article article = findArticle.get();

        articleLikeRepository.save(ArticleLike.builder()
                .article(article)
                .member(member).build());
    }

    public void deleteLike(long articleId, Member member){

        articleLikeRepository.deleteByArticleIdAndMember(articleId, member);
    }
}
