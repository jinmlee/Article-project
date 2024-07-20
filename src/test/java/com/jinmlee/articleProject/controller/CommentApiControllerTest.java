package com.jinmlee.articleProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinmlee.articleProject.dto.comment.AddCommentDto;
import com.jinmlee.articleProject.dto.member.CustomUserDetails;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.entity.comment.Comment;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.CommentRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();
    }

    @DisplayName("댓글 다는 기능 성공 테스트")
    @Transactional
    @Test
    public void addComment() throws Exception {

        final String url = "/api/article/{id}/comments";
        final String content = "testComment";

        Member savedMember = memberRepository.save(Member.builder()
                .name("testMember")
                .loginId("test1")
                .password(bCryptPasswordEncoder.encode("Test12345!@"))
                .email("test@test")
                .phoneNumber("010-1234-1234")
                .role(Role.USER)
                .build());

        Article savedArticle = articleRepository.save(Article.builder()
                .title("testArticle")
                .content("testContent")
                .member(savedMember)
                .build());


        final String requestBody = objectMapper.writeValueAsString(new AddCommentDto(content));

        CustomUserDetails customUserDetails = new CustomUserDetails(savedMember);

        mockMvc.perform(post(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .with(user(customUserDetails)))
                .andExpect(status().isCreated());

        List<Comment> commentList = commentRepository.findAll();

        assertThat(commentList.size()).isEqualTo(1);
        assertThat(commentList.get(0).getMember().getLoginId()).isEqualTo(savedMember.getLoginId());
        assertThat(commentList.get(0).getContent()).isEqualTo(content);
        assertThat(commentList.get(0).getArticle().getId()).isEqualTo(savedArticle.getId());
    }
}
