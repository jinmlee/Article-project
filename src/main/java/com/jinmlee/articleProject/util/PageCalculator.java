package com.jinmlee.articleProject.util;

public class PageCalculator {

    public static int calculateValidPageNumber(int pageNumber, long totalObject, int pageSize) {
        int totalPage = (int) ((totalObject + pageSize - 1) / pageSize);

        if (pageNumber > totalPage) {
            return totalPage;
        } else if (pageNumber < 1) {
            return 1;
        }
        return pageNumber;
    }
}
