package com.jinmlee.articleProject.service;

import com.jinmlee.articleProject.entity.article.Article;
import com.jinmlee.articleProject.entity.alarm.EditLimitedLineAlarm;
import com.jinmlee.articleProject.repository.AlarmRepository;
import com.jinmlee.articleProject.repository.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final ArticleRepository articleRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void addEditLimitedLine(){

        Instant nineDaysStart = Instant.now().minus(9, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        Instant nineDaysEnd = nineDaysStart.plus(1, ChronoUnit.DAYS);

        List<Article> articles = articleRepository.findEditLimitedLineArticle(nineDaysStart, nineDaysEnd);

        List<EditLimitedLineAlarm> editLimitedLineAlarms = articles.stream()
                .map(article -> new EditLimitedLineAlarm(article.getMember(), article, "수정 남은 기한이 9일 남았습니다."))
                .toList();

        alarmRepository.saveAll(editLimitedLineAlarms);
    }
}
