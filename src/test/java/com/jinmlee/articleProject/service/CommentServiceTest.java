package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.comment.AddCommentDto;
import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.comment.Comment;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;


    @Test
    @DisplayName("게시글 답글 저장하는 기능 테스트")
    void addComment() {
        //given

        String content = "content";
        AddCommentDto addCommentDto = new AddCommentDto(content);

        Article article = Article.builder()
                .id(1L).build();

        Member member = Member.builder()
                .id(1L).build();

        Comment comment = Comment.builder()
                .id(1L)
                .member(member)
                .article(article).build();

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        //when
        Comment result = commentService.save(addCommentDto, article, member);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMember().getId()).isEqualTo(1L);
        assertThat(result.getArticle().getId()).isEqualTo(1L);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() {
        // given
        long commentId = 1L;

        // when
        commentService.delete(commentId);

        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("댓글 작성자가 일치하는 테스트")
    void deleteValidUser1() {
        //given

        Member member = Member.builder()
                .id(1L).build();

        Comment comment = Comment.builder()
                .id(1L)
                .member(member).build();

        when(commentRepository.findById(any(long.class))).thenReturn(Optional.ofNullable(comment));

        //when
        commentService.isAuthor(member, comment.getId());

        //then
        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @Test
    @DisplayName("댓글 작성자가 불일치")
    void deleteValidUser2() {
        //given

        Member member = Member.builder()
                .id(1L).build();

        Member anotherMember = Member.builder()
                .id(2L).build();

        Comment comment = Comment.builder()
                .id(1L)
                .member(member).build();

        when(commentRepository.findById(any(long.class))).thenReturn(Optional.ofNullable(comment));

        //when
        assertThatThrownBy(() -> commentService.isAuthor(anotherMember, comment.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(commentRepository, times(1)).findById(comment.getId());
    }

    @Test
    @DisplayName("관리자인 경우")
    void deleteValidUser3() {
        //given
        Member member = Member.builder()
                .id(1L).build();

        Member anotherMember = Member.builder()
                .id(2L)
                .role(Role.ADMIN).build();

        Comment comment = Comment.builder()
                .id(1L)
                .member(member).build();

//        when(commentRepository.findById(any(long.class))).thenReturn(Optional.ofNullable(comment));

        //when
        commentService.isAuthor(anotherMember, comment.getId());

        //then
//        verify(commentRepository, times(1)).findById(comment.getId());
    }
}