package com.jinmlee.articleProject.entity.alarm;

import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Getter
@Builder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String message;

    private Instant createdDate;

    private boolean read;

    public Alarm(Member member, String message){
        this.member = member;
        this.message = message;
        this.createdDate = Instant.now();
    }
}
