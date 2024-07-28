package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto {

    private static final int PAGE_SIZE = 10;
    private static final int PAGE_GROUP_SIZE = 10;

    private int pageNumber;
    private boolean isNextPage;
    private boolean isPreviousPage;
    private int totalPage;
    private List<ArticleViewListDto> articleList;

    public int getPageSize() {
        return PAGE_SIZE;
    }

    public int getPageGroupSize() {
        return PAGE_GROUP_SIZE;
    }

    public ArticlePageDto updateDto(Page<Article> articleList) {
        this.pageNumber = articleList.getNumber();
        this.isNextPage = articleList.hasNext();
        this.isPreviousPage = articleList.hasPrevious();
        this.totalPage = articleList.getTotalPages();
        this.articleList = articleList.stream().map(ArticleViewListDto::new).toList();

        return this;
    }
}
