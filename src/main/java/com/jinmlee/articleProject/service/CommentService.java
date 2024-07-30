package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.comment.AddCommentDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.entity.comment.Comment;
import com.jinmlee.articleProject.enums.Role;
import com.jinmlee.articleProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(AddCommentDto addCommentDto, Article article, Member member) {

        return commentRepository.save(addCommentDto.toEntity(article, member));

    }

    public void delete(long id) {
        commentRepository.deleteById(id);
    }

    public void isAuthor(Member member, long commentId) {
        if (member.getRole() == Role.ADMIN)
            return;

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("not found comment : " + commentId));

        if (!comment.getMember().equals(member)) {
            throw new IllegalArgumentException("댓글 작성자만 삭제 가능합니다.");
        }
    }
}
