package com.jinmlee.articleProject.entity;

import com.jinmlee.articleProject.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "hits")
    private long hits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getModifyLimitedDate() {

        Instant timeNow = Instant.now();

        long limitedDate = 10 - ChronoUnit.DAYS.between(getCreatedDate(), timeNow);
        if (limitedDate <= 0) {
            return 0;
        }

        return limitedDate;
    }

    public boolean isEditable() {

        Instant timeNow = Instant.now();

        return ChronoUnit.DAYS.between(getCreatedDate(), timeNow) <= 10;
    }
}
