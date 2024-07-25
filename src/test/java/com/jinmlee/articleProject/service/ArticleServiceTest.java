package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.ArticlePageDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.enums.ArticleSortType;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void saveArticle() {

        //given
        AddArticleDto addArticleDto = new AddArticleDto("title", "content");

        Member member = Member.builder()
                .id(1L)
                .build();

        Article article = Article.builder()
                .id(1L)
                .member(member)
                .title(addArticleDto.getTitle())
                .content(addArticleDto.getContent())
                .build();

        when(articleRepository.save(any(Article.class))).thenReturn(article);

        //when
        Article savedArticle = articleService.save(addArticleDto, member);

        //then
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isEqualTo(1L);

        verify(articleRepository, times(1)).save(any(Article.class));

    }

    @Test
    @DisplayName("게시글 리스트를 가져오는 테스트")
    void getArticleList() {
        //given
        int page = 1;
        long totalArticles = 30;
        ArticleSortType sortType = ArticleSortType.CREATED_DESC;
        ArticlePageDto pageDto = new ArticlePageDto(page);

        Article article = new Article();
        List<Article> articleList = List.of(article);

        when(articleRepository.count()).thenReturn(totalArticles);
        Pageable pageable = articleService.createPageRequest(sortType, pageDto);
        Page<Article> mockPage = new PageImpl<>(articleList, pageable, totalArticles);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(mockPage);


        //when
        Page<Article> result = articleService.getList(pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(result.getContent().size()).isEqualTo(articleList.size());

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(articleRepository, times(1)).count();
    }

    @Test
    @DisplayName("경계 조건 테스트: 마지막 페이지")
    void getList_LastPage() {

        //given
        int page = 3;
        long totalArticles = 30;
        ArticleSortType sortType = ArticleSortType.CREATED_DESC;
        ArticlePageDto pageDto = new ArticlePageDto(page);

        when(articleRepository.count()).thenReturn(totalArticles);
        Pageable pageable = articleService.createPageRequest(sortType, pageDto);

        List<Article> articleList = List.of(Article.builder().id(1L).build());

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(articleList, pageable, totalArticles));


        //when
        Page<Article> result = articleService.getList(pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(page - 1);
        assertThat(result.getNumber()).isEqualTo(pageDto.getPageNumber());


        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(articleRepository, times(1)).count();
    }

    @Test
    @DisplayName("예외 페이지 마이너스")
    void getList_invalidPage() {

        //given
        int page = -1;
        long totalArticles = 30;
        ArticleSortType sortType = ArticleSortType.CREATED_DESC;
        ArticlePageDto pageDto = new ArticlePageDto(page);

        when(articleRepository.count()).thenReturn(totalArticles);
        Pageable pageable = articleService.createPageRequest(sortType, pageDto);

        List<Article> articleList = List.of(Article.builder().id(1L).build());

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(articleList, pageable, totalArticles));


        //when
        Page<Article> result = articleService.getList(pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getNumber()).isEqualTo(pageDto.getPageNumber());


        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(articleRepository, times(1)).count();
    }

    @Test
    @DisplayName("예외 페이지 오버 페이지")
    void getList_invalidPage2() {

        //given
        int page = 100;
        long totalArticles = 36;
        ArticleSortType sortType = ArticleSortType.CREATED_DESC;
        ArticlePageDto pageDto = new ArticlePageDto(page);

        when(articleRepository.count()).thenReturn(totalArticles);
        List<Article> articleList = List.of(Article.builder().id(1L).build());
        Pageable pageable = articleService.createPageRequest(sortType, pageDto);

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(articleList, pageable, totalArticles));


        //when
        Page<Article> result = articleService.getList(pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(3);
        assertThat(result.getNumber()).isEqualTo(pageDto.getPageNumber());


        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(articleRepository, times(1)).count();
    }

    @Test
    @DisplayName("PageRequest 생성 테스트")
    void createPageRequest() {
        //given
        ArticleSortType sortType = ArticleSortType.CREATED_DESC;
        ArticlePageDto pageDto = new ArticlePageDto(1);

        when(articleRepository.count()).thenReturn(30L);

        //when
        Pageable pageable = articleService.createPageRequest(sortType, pageDto);

        //then
        assertThat(pageable.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(pageable.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(Objects.requireNonNull(pageable.getSort().getOrderFor(sortType.getField())).getDirection()).isEqualTo(Sort.Direction.DESC);

        verify(articleRepository, times(1)).count();
    }


    @Test
    @DisplayName("게시글 업데이트 성공 테스트")
    void updateArticle_Success() {
        //given
        long articleId = 1L;
        UpdateArticleDto updateArticleDto = new UpdateArticleDto("updatedTitle", "updatedContent");

        Article article = Article.builder()
                .id(articleId)
                .title("originalTitle")
                .content("originalContent")
                .build();

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        //when
        Article updatedArticle = articleService.update(articleId, updateArticleDto);

        //then
        assertThat(updatedArticle).isNotNull();
        assertThat(updatedArticle.getTitle()).isEqualTo("updatedTitle");
        assertThat(updatedArticle.getContent()).isEqualTo("updatedContent");

        verify(articleRepository, times(1)).findById(articleId);
    }

    @Test
    @DisplayName("게시글 업데이트 실패 테스트: 존재하지 않는 게시글")
    void updateArticle_NotFound() {
        //given
        long articleId = 1L;
        UpdateArticleDto updateArticleDto = new UpdateArticleDto("updatedTitle", "updatedContent");

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            articleService.update(articleId, updateArticleDto);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo("not found: " + articleId);

        verify(articleRepository, times(1)).findById(articleId);
    }
}