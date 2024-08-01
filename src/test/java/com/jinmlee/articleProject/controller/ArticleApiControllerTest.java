package com.jinmlee.articleProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinmlee.articleProject.dto.article.AddArticleDto;
import com.jinmlee.articleProject.dto.article.UpdateArticleDto;
import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.article.ArticleLike;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.member.MemberInfo;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.article.ArticleLikeRepository;
import com.jinmlee.articleProject.repository.article.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberInfoRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        articleLikeRepository.deleteAll();
        articleRepository.deleteAll();
        memberInfoRepository.deleteAll();
        memberRepository.deleteAll();

        Member savedMember = memberRepository.save(Member.builder()
                .loginId("test1")
                .password(bCryptPasswordEncoder.encode("Test12345!@"))
                .role(Role.USER)
                .build());

        MemberInfo savedMemberInfo = memberInfoRepository.save(MemberInfo.builder()
                .member(savedMember)
                .name("name")
                .phoneNumber("010-1234-1234")
                .email("test@test").build());
    }

    @DisplayName("게시판 글 추가 성공 테스트")
    @Test
    public void addArticle() throws Exception {
        final String url = "/api/articles";
        final String title = "test1";
        final String content = "test1 content";

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        final AddArticleDto addArticleDto = new AddArticleDto(title, content);

        final String requestBody = objectMapper.writeValueAsString(addArticleDto);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(user(customUserDetails)));

        result.andExpect(status().isCreated());

        List<Article> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
        assertThat(articles.get(0).getMember().getId()).isEqualTo(loggedMember.getId());
    }

    @DisplayName("개시판 글 목록 정렬 조회 성공 테스트")
    @Test
    public void findAllArticle() throws Exception {
        final String url = "/api/articles";

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        articleRepository.save(Article.builder()
                .title("title1")
                .content("content1")
                .member(loggedMember)
                .build());
        articleRepository.save(Article.builder()
                .title("title2")
                .content("content2")
                .member(loggedMember)
                .build());
        articleRepository.save(Article.builder()
                .title("title3")
                .content("content3")
                .member(loggedMember)
                .build());

        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(customUserDetails))
                .param("page", "1")
                .param("sortType", "CREATED_DESC"));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleList[0].title").value("title3"))
                .andExpect(jsonPath("$.articleList[1].title").value("title2"))
                .andExpect(jsonPath("$.articleList[2].title").value("title1"));
    }

    @DisplayName("게시글 조회 성공 테스트")
    @Test
    public void findArticle() throws Exception {
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        Article savedArticle = articleRepository.save(Article.builder()
                .content(content)
                .title(title)
                .member(loggedMember)
                .build());

        ResultActions result = mockMvc.perform(get(url, savedArticle.getId())
                .with(user(customUserDetails)));

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

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

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
                .with(user(customUserDetails))
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

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        Article savedArticle = articleRepository.save(Article.builder()
                .title(title)
                .content(content)
                .member(loggedMember)
                .build());

        ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId())
                .with(user(customUserDetails)));

        resultActions
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 좋아요 기능 테스트")
    @Test
    void addLike() throws Exception {
        //given
        final String url = "/api/article/{id}/like";

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        Article article = articleRepository.save(Article.builder()
                .member(loggedMember)
                .hits(0)
                .title("title")
                .content("content")
                .deletedAt(null)
                .build());

        //when

        ResultActions resultActions = mockMvc.perform(post(url, article.getId())
                .with(user(customUserDetails)));

        List<ArticleLike> articleLikes = articleLikeRepository.findAll();
        //then

        resultActions
                .andExpect(status().isOk());

        assertThat(articleLikes.size()).isEqualTo(1);
    }


    @DisplayName("게시글 삭제 기능 테스트")
    @Test
    void deleteLike() throws Exception {
        //given
        final String url = "/api/article/{id}/like";

        List<Member> findMembers = memberRepository.findAll();
        Member loggedMember = findMembers.get(0);
        CustomUserDetails customUserDetails = new CustomUserDetails(loggedMember);

        Article article = articleRepository.save(Article.builder()
                .member(loggedMember)
                .hits(0)
                .title("title")
                .content("content")
                .deletedAt(null)
                .build());

        articleLikeRepository.save(ArticleLike.builder()
                .member(loggedMember)
                .article(article).build());

        //when

        ResultActions resultActions = mockMvc.perform(post(url, article.getId())
                .with(user(customUserDetails)));

        List<ArticleLike> articleLikes = articleLikeRepository.findAll();
        //then

        resultActions
                .andExpect(status().isOk());

        assertThat(articleLikes.size()).isEqualTo(0);
    }
}