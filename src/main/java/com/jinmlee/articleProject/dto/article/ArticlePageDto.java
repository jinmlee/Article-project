package com.jinmlee.articleProject.dto.article;

import com.jinmlee.articleProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public ArticlePageDto(int pageNumber){
        this.pageNumber = pageNumber - 1;
    }

    public int getPageSize(){
        return PAGE_SIZE;
    }

    public int getPageGroupSize(){
        return PAGE_GROUP_SIZE;
    }

    public void updateDto(Page<Article> articleList){
        this.pageNumber = articleList.getNumber();
        this.isNextPage = articleList.hasNext();
        this.isPreviousPage = articleList.hasPrevious();
        this.totalPage = articleList.getTotalPages();
    }

    public void isValidPage(long totalArticles){
        int totalPages = (int)((totalArticles + PAGE_SIZE - 1) / PAGE_SIZE);
        if(pageNumber + 1 > totalPages){
            pageNumber = totalPages - 1;
        } else if (pageNumber < 0) {
            pageNumber = 0;
        }
    }
}
