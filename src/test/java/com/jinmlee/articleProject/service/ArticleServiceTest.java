package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.ArticleRepository;
import com.jinmlee.articleProject.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp(){
        articleRepository.deleteAll();
        memberRepository.deleteAll();

        Member savedMember1 = memberRepository.save(Member.builder()
                .name("testMember1")
                .loginId("test1")
                .password(bCryptPasswordEncoder.encode("Test12345!@"))
                .email("test@test")
                .phoneNumber("010-1234-1234")
                .role(Role.USER)
                .build());

        Member savedMember2 = memberRepository.save(Member.builder()
                .name("testMember2")
                .loginId("test2")
                .password(bCryptPasswordEncoder.encode("Test12345!@"))
                .email("test@test")
                .phoneNumber("010-1234-1234")
                .role(Role.USER)
                .build());

        Member savedMember3 = memberRepository.save(Member.builder()
                .name("testMember3")
                .loginId("test3")
                .password(bCryptPasswordEncoder.encode("Test12345!@"))
                .email("test@test")
                .phoneNumber("010-1234-1234")
                .role(Role.USER)
                .build());

        articleRepository.save(Article.builder()
                        .title("title")
                        .content("content")
                        .hits(0)
                        .member(savedMember1)
                        .build());

    }

    @Test
    void incrementViewCount() throws InterruptedException {

        List<Member> memberList = memberRepository.findAll();

        for(Member member : memberList){
            System.out.println(member.getId());
        }

        List<Article> articleList = articleRepository.findAll();

        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(0).getId());
        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(0).getId());
        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(1).getId());
        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(1).getId());
        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(2).getId());
        articleService.incrementViewCount(articleList.get(0).getId(), memberList.get(2).getId());

        articleService.updateViewCount();

        Article article = articleRepository.findById(articleList.get(0).getId()).orElseThrow();

        Thread.sleep(1000);

        assertThat(article.getHits()).isEqualTo(3);
    }
}