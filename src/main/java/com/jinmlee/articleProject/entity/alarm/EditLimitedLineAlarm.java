package com.jinmlee.articleProject.entity.alarm;

import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorColumn(name = "EDIT_LIMITED_LINE")
@Getter
@NoArgsConstructor
public class EditLimitedLineAlarm extends Alarm{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public EditLimitedLineAlarm(Member member, Article article, String message){
        super(member, message);
        this.article = article;
    }

}
