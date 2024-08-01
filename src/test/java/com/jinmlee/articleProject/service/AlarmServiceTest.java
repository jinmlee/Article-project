package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.entity.Article;
import com.jinmlee.articleProject.entity.alarm.EditLimitedLineAlarm;
import com.jinmlee.articleProject.entity.member.Member;
import com.jinmlee.articleProject.repository.AlarmRepository;
import com.jinmlee.articleProject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @Mock
    private AlarmRepository alarmRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private AlarmService alarmService;

    @Captor
    private ArgumentCaptor<List<EditLimitedLineAlarm>> alarmCaptor;

    @Test
    @DisplayName("article의 수정 제한 날짜 알림 테스트")
    void EditLimitedAlarm(){

        //given
        Instant nineDaysStart = Instant.now().minus(9, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        Instant nineDaysEnd = nineDaysStart.plus(1, ChronoUnit.DAYS);

        Member member = Member.builder()
                .id(1L).build();

        Article article = Article.builder()
                .id(1L)
                .member(member).build();

        when(articleRepository.findEditLimitedLineArticle(any(Instant.class), any(Instant.class))).thenReturn(List.of(article));

        //when
        alarmService.addEditLimitedLine();

        verify(alarmRepository, times(1)).saveAll(alarmCaptor.capture());
        verify(articleRepository, times(1)).findEditLimitedLineArticle(any(Instant.class), any(Instant.class));
    }
}