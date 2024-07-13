package com.jinmlee.articleProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        articleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("게시판 글 추가 성공 테스트")
    @Test
    public void addArticle() throws Exception{
        final String url = "/api/articles";
        final String title = "test1";
        final String content = "test1 content";

        Member loggedMember = memberRepository.save(Member.builder()
                .loginId("testId").name("testName").password("Test12345!@").phoneNumber("010-1234-1234").email("test@email").build());

        final AddArticleDto addArticleDto = new AddArticleDto(title, content);

        final String requestBody = objectMapper.writeValueAsString(addArticleDto);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .sessionAttr("loggedMember", new SessionMemberDto(loggedMember.getId(), loggedMember.getName())));

        result.andExpect(status().isCreated());

        List<Article> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
        assertThat(articles.get(0).getMember().getId()).isEqualTo(loggedMember.getId());
    }

    @DisplayName("개시판 글 목록 전체 조회 성공 테스트")
    @Test
    public void findAllArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        Member loggedMember = memberRepository.save(Member.builder()
                .loginId("testId").name("testName").password("Test12345!@").phoneNumber("010-1234-1234").email("test@email").build());

        articleRepository.save(Article.builder()
                .title(title)
                .content(content)
                .member(loggedMember)
                .build());

        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("게시글 조회 성공 테스트")
    @Test
    public void findArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Member loggedMember = memberRepository.save(Member.builder()
                .loginId("testId").name("testName").password("Test12345!@").phoneNumber("010-1234-1234").email("test@email").build());

        Article savedArticle = articleRepository.save(Article.builder()
                .content(content)
                .title(title)
                .member(loggedMember)
                .build());

        ResultActions result = mockMvc.perform(get(url, savedArticle.getId()));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("게시글 업데이트 기능 성공 테스트")
    @Test
    public void updateArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Member loggedMember = memberRepository.save(Member.builder()
                .loginId("testId").name("testName").password("Test12345!@").phoneNumber("010-1234-1234").email("test@email").build());

        Article savedArticle = articleRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                        .member(loggedMember)
                        .build());

        final String newTile = "newTitle";
        final String newContent = "newContent";

        UpdateArticleDto updateArticleDto = new UpdateArticleDto(newTile, newContent);

        ResultActions resultActions = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateArticleDto)));

        resultActions.andExpect(status().isOk());

        Article article = articleRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTile);
        assertThat(article.getContent()).isEqualTo(newContent);

    }

    @DisplayName("게시글 수정기한이 남은 테스트")
    @Test
    public void editAble() throws Exception {
        final String url = "/api/articles/{id}/editable";
        final String title = "title";
        final String content = "content";

        Member loggedMember = memberRepository.save(Member.builder()
                .loginId("testId").name("testName").password("Test12345!@").phoneNumber("010-1234-1234").email("test@email").build());

        Article savedArticle = articleRepository.save(Article.builder()
                .title(title)
                .content(content)
                .member(loggedMember)
                .build());

        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        resultActions
                .andExpect(status().isOk());
    }

}