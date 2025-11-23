package com.cholog_ai.closet_saver.domain.closet.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Category Enum 테스트")
class CategoryTest {

    @Test
    @DisplayName("모든 Category 상수가 정의되어 있음")
    void allCategoryValues_areDefined() {
        // when
        Category[] categories = Category.values();

        // then
        assertThat(categories).hasSize(5);
        assertThat(categories).contains(
                Category.KNIT,
                Category.OUTER,
                Category.SHIRT,
                Category.PANTS,
                Category.ONEPIECE
        );
    }

    @ParameterizedTest
    @CsvSource({
            "KNIT, 1",
            "OUTER, 2",
            "SHIRT, 3",
            "PANTS, 4",
            "ONEPIECE, 5"
    })
    @DisplayName("각 Category는 올바른 index를 반환")
    void getIndex_returnsCorrectValue(Category category, int expectedIndex) {
        assertThat(category.getIndex()).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @EnumSource(Category.class)
    @DisplayName("모든 Category의 index는 양수")
    void getIndex_forAllCategories_isPositive(Category category) {
        assertThat(category.getIndex()).isPositive();
    }

    @Test
    @DisplayName("Category.valueOf()로 문자열에서 enum 변환 가능")
    void valueOf_withValidString_returnsEnum() {
        // when
        Category category = Category.valueOf("KNIT");

        // then
        assertThat(category).isEqualTo(Category.KNIT);
        assertThat(category.getIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Category.valueOf()에 잘못된 문자열 입력 시 예외 발생")
    void valueOf_withInvalidString_throwsException() {
        assertThatThrownBy(() -> Category.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("각 Category의 index는 고유함")
    void allCategories_haveUniqueIndices() {
        // when
        int[] indices = new int[Category.values().length];
        for (int i = 0; i < Category.values().length; i++) {
            indices[i] = Category.values()[i].getIndex();
        }

        // then
        assertThat(indices).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Category enum은 순서가 정의되어 있음")
    void categoryOrdering_isConsistent() {
        // when
        Category[] categories = Category.values();

        // then
        assertThat(categories[0]).isEqualTo(Category.KNIT);
        assertThat(categories[1]).isEqualTo(Category.OUTER);
        assertThat(categories[2]).isEqualTo(Category.SHIRT);
        assertThat(categories[3]).isEqualTo(Category.PANTS);
        assertThat(categories[4]).isEqualTo(Category.ONEPIECE);
    }

    @ParameterizedTest
    @EnumSource(Category.class)
    @DisplayName("모든 Category는 toString()으로 이름을 반환")
    void toString_returnsCorrectName(Category category) {
        assertThat(category.toString()).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 Category 인스턴스는 같음")
    void sameCategory_isEqual() {
        // given
        Category category1 = Category.KNIT;
        Category category2 = Category.KNIT;

        // then
        assertThat(category1).isEqualTo(category2);
        assertThat(category1).isSameAs(category2);
    }

    @Test
    @DisplayName("다른 Category 인스턴스는 다름")
    void differentCategories_areNotEqual() {
        assertThat(Category.KNIT).isNotEqualTo(Category.OUTER);
    }
}