package com.jinmlee.articleProject.service.article;

import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.article.ArticleLike;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import com.jinmlee.articleProject.repository.article.ArticleLikeRepository;
import com.jinmlee.articleProject.repository.article.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleLikeServiceTest {

    @InjectMocks
    private ArticleLikeService articleLikeService;

    @Mock
    private ArticleLikeRepository articleLikeRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 좋아요 누르는 기능 테스트")
    void addLike(){
        //given
        Member member = Member.builder()
                .id(1L).build();

        Article article = Article.builder()
                .id(1L)
                .build();

        ArticleLike articleLike = ArticleLike.builder()
                .article(article)
                .member(member).build();

        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(article));

        //when
        articleLikeService.addLike(1L, member);

        //then
        verify(articleLikeRepository, times(1)).save(any(ArticleLike.class));
        verify(articleRepository,times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("게시글 좋아요 취소 기능 테스트")
    void deleteLike(){
        //given
        Member member = Member.builder()
                .id(1L).build();

        Article article = Article.builder()
                .id(1L).build();
        //when

        articleLikeService.deleteLike(article.getId(), member);

        //then

        verify(articleLikeRepository, times(1)).deleteByArticleIdAndMember(article.getId(), member);
    }
}