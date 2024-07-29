package com.jinmlee.articleProject.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageCalculatorTest {

    @Test
    @DisplayName("페이지 번호과 유효범위 초과할때 마지막 페이지로 설정")
    void calculatorValidPageNumber1() {
        //given
        int pageNumber = 1000;
        long totalObject = 37;
        int pageSize = 10;

        //when
        int result = PageCalculator.calculateValidPageNumber(pageNumber, totalObject, pageSize);

        //then
        assertThat(result).isEqualTo(4);

    }

    @Test
    @DisplayName("페이지 번호과 마이너스일때 페이지 1로 설정")
    void calculatorValidPageNumber2() {
        //given
        int pageNumber = -1;
        long totalObject = 37;
        int pageSize = 10;

        //when
        int result = PageCalculator.calculateValidPageNumber(pageNumber, totalObject, pageSize);

        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("페이지 번호 경계 마지막")
    void calculatorValidPageNumber3() {
        //given
        int pageNumber = 4;
        long totalObject = 37;
        int pageSize = 10;

        //when
        int result = PageCalculator.calculateValidPageNumber(pageNumber, totalObject, pageSize);

        //then
        assertThat(result).isEqualTo(4);

    }

    @Test
    @DisplayName("페이지 번호 경계 첫 페이지")
    void calculatorValidPageNumber4() {
        //given
        int pageNumber = 1;
        long totalObject = 37;
        int pageSize = 10;

        //when
        int result = PageCalculator.calculateValidPageNumber(pageNumber, totalObject, pageSize);

        //then
        assertThat(result).isEqualTo(1);
    }
}