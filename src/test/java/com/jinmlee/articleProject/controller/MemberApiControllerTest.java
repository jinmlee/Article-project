package com.jinmlee.articleProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinmlee.articleProject.dto.AddMemberDto;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        memberRepository.deleteAll();
    }


    @DisplayName("회원가입 성공 테스트")
    @ParameterizedTest
    @CsvSource({
            "강호동, test1, Test12345!@, 010-1234-5678, test1@test",
            "유재석, test2, Test56789@#, 010-2345-6789, test2@test",
            "이수근, test3, Test67543*&, 010-3456-7890, test3@test"
    })
    public void addMember(String name, String loginId, String password, String phoneNumber, String email) throws Exception{
        final String url = "/api/members";
        AddMemberDto addMemberDto = new AddMemberDto(name, loginId, password, phoneNumber, email);

        final String requestBody = objectMapper.writeValueAsString(addMemberDto);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isCreated());

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getName()).isEqualTo(name);
        assertThat(members.get(0).getLoginId()).isEqualTo(loginId);
        assertThat(members.get(0).getPassword()).isEqualTo(password);
        assertThat(members.get(0).getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(members.get(0).getEmail()).isEqualTo(email);
    }
}