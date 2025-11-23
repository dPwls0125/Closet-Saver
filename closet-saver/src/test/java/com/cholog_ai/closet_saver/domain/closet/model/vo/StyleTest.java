package com.cholog_ai.closet_saver.domain.closet.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Style Enum 테스트")
class StyleTest {

    @Test
    @DisplayName("모든 Style 상수가 정의되어 있음")
    void allStyleValues_areDefined() {
        // when
        Style[] styles = Style.values();

        // then
        assertThat(styles).hasSize(5);
        assertThat(styles).contains(
                Style.MINIMAL,
                Style.CASUAL,
                Style.STREET,
                Style.CLASSIC,
                Style.FEMININE
        );
    }

    @ParameterizedTest
    @CsvSource({
            "MINIMAL, 1",
            "CASUAL, 2",
            "STREET, 3",
            "CLASSIC, 4",
            "FEMININE, 5"
    })
    @DisplayName("각 Style은 올바른 index를 반환")
    void getIndex_returnsCorrectValue(Style style, int expectedIndex) {
        assertThat(style.getIndex()).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @EnumSource(Style.class)
    @DisplayName("모든 Style의 index는 양수")
    void getIndex_forAllStyles_isPositive(Style style) {
        assertThat(style.getIndex()).isPositive();
    }

    @Test
    @DisplayName("Style.valueOf()로 문자열에서 enum 변환 가능")
    void valueOf_withValidString_returnsEnum() {
        // when
        Style style = Style.valueOf("MINIMAL");

        // then
        assertThat(style).isEqualTo(Style.MINIMAL);
        assertThat(style.getIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Style.valueOf()에 잘못된 문자열 입력 시 예외 발생")
    void valueOf_withInvalidString_throwsException() {
        assertThatThrownBy(() -> Style.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("각 Style의 index는 고유함")
    void allStyles_haveUniqueIndices() {
        // when
        int[] indices = new int[Style.values().length];
        for (int i = 0; i < Style.values().length; i++) {
            indices[i] = Style.values()[i].getIndex();
        }

        // then
        assertThat(indices).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Style enum은 순서가 정의되어 있음")
    void styleOrdering_isConsistent() {
        // when
        Style[] styles = Style.values();

        // then
        assertThat(styles[0]).isEqualTo(Style.MINIMAL);
        assertThat(styles[1]).isEqualTo(Style.CASUAL);
        assertThat(styles[2]).isEqualTo(Style.STREET);
        assertThat(styles[3]).isEqualTo(Style.CLASSIC);
        assertThat(styles[4]).isEqualTo(Style.FEMININE);
    }

    @ParameterizedTest
    @EnumSource(Style.class)
    @DisplayName("모든 Style은 toString()으로 이름을 반환")
    void toString_returnsCorrectName(Style style) {
        assertThat(style.toString()).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 Style 인스턴스는 같음")
    void sameStyle_isEqual() {
        // given
        Style style1 = Style.MINIMAL;
        Style style2 = Style.MINIMAL;

        // then
        assertThat(style1).isEqualTo(style2);
        assertThat(style1).isSameAs(style2);
    }

    @Test
    @DisplayName("다른 Style 인스턴스는 다름")
    void differentStyles_areNotEqual() {
        assertThat(Style.MINIMAL).isNotEqualTo(Style.CASUAL);
    }
}