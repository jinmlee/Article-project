package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.dto.member.SessionMemberDto;
import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewDto {
    private Long id;
    private String title;
    private String content;
    private long memberId;
    private String memberName;

    public ArticleViewDto(Article article, SessionMemberDto sessionMemberDto){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.memberId = sessionMemberDto.getId();
        this.memberName = sessionMemberDto.getName();
    }
}
