package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.dto.comment.AddCommentDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import com.jinmlee.articleProject.entity.comment.Comment;
import com.jinmlee.articleProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(AddCommentDto addCommentDto, Article article, Member member){

        return commentRepository.save(addCommentDto.toEntity(article, member));

    }
}
