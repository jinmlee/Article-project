package com.jinmlee.articleProject.entity.article;

import com.jinmlee.articleProject.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(
        name = "article_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "article_id"})
        },
        indexes = {
                @Index(name = "idx_member_article", columnList = "member_id, article_id"),
                @Index(name = "idx_article", columnList = "article_id")
        }
)
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "article_id", nullable = false)
    private Long articleId;
}
