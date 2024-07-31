package com.jinmlee.articleProject.dto.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleViewDto {
    private Long id;
    private String title;
    private String content;
    private long memberId;
    private String author;
    private long hits;
    private Instant createdDate;
    private long modifyLimitedDate;

    public ArticleViewDto(Long id, String title, String content,
                          long memberId, String author, long hits, Instant createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.author = author;
        this.hits = hits;
        this.createdDate = createdDate;
    }

    public void setModifyLimitedDate() {
        Instant timeNow = Instant.now();

        long limitedDate = 10 - ChronoUnit.DAYS.between(createdDate, timeNow);
        if (limitedDate <= 0) {
            this.modifyLimitedDate = 0;
            return;
        }

        this.modifyLimitedDate = limitedDate;
    }
}
