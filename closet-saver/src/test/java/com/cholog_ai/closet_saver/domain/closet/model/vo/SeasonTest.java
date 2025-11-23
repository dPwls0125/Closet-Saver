package com.cholog_ai.closet_saver.domain.closet.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Season Enum 테스트")
class SeasonTest {

    @Test
    @DisplayName("모든 Season 상수가 정의되어 있음")
    void allSeasonValues_areDefined() {
        // when
        Season[] seasons = Season.values();

        // then
        assertThat(seasons).hasSize(4);
        assertThat(seasons).contains(
                Season.SPRING,
                Season.SUMMER,
                Season.FALL,
                Season.WINTER
        );
    }

    @ParameterizedTest
    @CsvSource({
            "SPRING, 1",
            "SUMMER, 2",
            "FALL, 3",
            "WINTER, 4"
    })
    @DisplayName("각 Season은 올바른 index를 반환")
    void getIndex_returnsCorrectValue(Season season, int expectedIndex) {
        assertThat(season.getIndex()).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @EnumSource(Season.class)
    @DisplayName("모든 Season의 index는 양수")
    void getIndex_forAllSeasons_isPositive(Season season) {
        assertThat(season.getIndex()).isPositive();
    }

    @Test
    @DisplayName("Season.valueOf()로 문자열에서 enum 변환 가능")
    void valueOf_withValidString_returnsEnum() {
        // when
        Season season = Season.valueOf("SPRING");

        // then
        assertThat(season).isEqualTo(Season.SPRING);
        assertThat(season.getIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Season.valueOf()에 잘못된 문자열 입력 시 예외 발생")
    void valueOf_withInvalidString_throwsException() {
        assertThatThrownBy(() -> Season.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("각 Season의 index는 고유함")
    void allSeasons_haveUniqueIndices() {
        // when
        int[] indices = new int[Season.values().length];
        for (int i = 0; i < Season.values().length; i++) {
            indices[i] = Season.values()[i].getIndex();
        }

        // then
        assertThat(indices).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Season enum은 순서가 정의되어 있음")
    void seasonOrdering_isConsistent() {
        // when
        Season[] seasons = Season.values();

        // then
        assertThat(seasons[0]).isEqualTo(Season.SPRING);
        assertThat(seasons[1]).isEqualTo(Season.SUMMER);
        assertThat(seasons[2]).isEqualTo(Season.FALL);
        assertThat(seasons[3]).isEqualTo(Season.WINTER);
    }

    @ParameterizedTest
    @EnumSource(Season.class)
    @DisplayName("모든 Season은 toString()으로 이름을 반환")
    void toString_returnsCorrectName(Season season) {
        assertThat(season.toString()).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 Season 인스턴스는 같음")
    void sameSeason_isEqual() {
        // given
        Season season1 = Season.SPRING;
        Season season2 = Season.SPRING;

        // then
        assertThat(season1).isEqualTo(season2);
        assertThat(season1).isSameAs(season2);
    }

    @Test
    @DisplayName("다른 Season 인스턴스는 다름")
    void differentSeasons_areNotEqual() {
        assertThat(Season.SPRING).isNotEqualTo(Season.SUMMER);
    }

    @Test
    @DisplayName("Season은 계절의 순환을 나타냄")
    void seasons_representCyclicalOrder() {
        // when
        Season[] seasons = Season.values();

        // then - 봄 -> 여름 -> 가을 -> 겨울 순서
        assertThat(seasons).containsExactly(
                Season.SPRING,
                Season.SUMMER,
                Season.FALL,
                Season.WINTER
        );
    }
}