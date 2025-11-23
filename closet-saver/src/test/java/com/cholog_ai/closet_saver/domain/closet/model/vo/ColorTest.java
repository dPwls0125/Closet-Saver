package com.cholog_ai.closet_saver.domain.closet.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Color Enum 테스트")
class ColorTest {

    @Test
    @DisplayName("모든 Color 상수가 정의되어 있음")
    void allColorValues_areDefined() {
        // when
        Color[] colors = Color.values();

        // then
        assertThat(colors).hasSize(5);
        assertThat(colors).contains(
                Color.WHITE,
                Color.BLACK,
                Color.BEIGE,
                Color.GREY,
                Color.BLUE
        );
    }

    @ParameterizedTest
    @CsvSource({
            "WHITE, 1",
            "BLACK, 2",
            "BEIGE, 3",
            "GREY, 4",
            "BLUE, 5"
    })
    @DisplayName("각 Color는 올바른 index를 반환")
    void getIndex_returnsCorrectValue(Color color, int expectedIndex) {
        assertThat(color.getIndex()).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    @DisplayName("모든 Color의 index는 양수")
    void getIndex_forAllColors_isPositive(Color color) {
        assertThat(color.getIndex()).isPositive();
    }

    @Test
    @DisplayName("Color.valueOf()로 문자열에서 enum 변환 가능")
    void valueOf_withValidString_returnsEnum() {
        // when
        Color color = Color.valueOf("WHITE");

        // then
        assertThat(color).isEqualTo(Color.WHITE);
        assertThat(color.getIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Color.valueOf()에 잘못된 문자열 입력 시 예외 발생")
    void valueOf_withInvalidString_throwsException() {
        assertThatThrownBy(() -> Color.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("각 Color의 index는 고유함")
    void allColors_haveUniqueIndices() {
        // when
        int[] indices = new int[Color.values().length];
        for (int i = 0; i < Color.values().length; i++) {
            indices[i] = Color.values()[i].getIndex();
        }

        // then
        assertThat(indices).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Color enum은 순서가 정의되어 있음")
    void colorOrdering_isConsistent() {
        // when
        Color[] colors = Color.values();

        // then
        assertThat(colors[0]).isEqualTo(Color.WHITE);
        assertThat(colors[1]).isEqualTo(Color.BLACK);
        assertThat(colors[2]).isEqualTo(Color.BEIGE);
        assertThat(colors[3]).isEqualTo(Color.GREY);
        assertThat(colors[4]).isEqualTo(Color.BLUE);
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    @DisplayName("모든 Color는 toString()으로 이름을 반환")
    void toString_returnsCorrectName(Color color) {
        assertThat(color.toString()).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 Color 인스턴스는 같음")
    void sameColor_isEqual() {
        // given
        Color color1 = Color.WHITE;
        Color color2 = Color.WHITE;

        // then
        assertThat(color1).isEqualTo(color2);
        assertThat(color1).isSameAs(color2);
    }

    @Test
    @DisplayName("다른 Color 인스턴스는 다름")
    void differentColors_areNotEqual() {
        assertThat(Color.WHITE).isNotEqualTo(Color.BLACK);
    }
}