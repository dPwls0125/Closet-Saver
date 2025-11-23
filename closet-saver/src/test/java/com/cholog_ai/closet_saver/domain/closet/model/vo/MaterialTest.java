package com.cholog_ai.closet_saver.domain.closet.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Material Enum 테스트")
class MaterialTest {

    @Test
    @DisplayName("모든 Material 상수가 정의되어 있음")
    void allMaterialValues_areDefined() {
        // when
        Material[] materials = Material.values();

        // then
        assertThat(materials).hasSize(5);
        assertThat(materials).contains(
                Material.COTTON,
                Material.WOOL,
                Material.POLY,
                Material.LINEN,
                Material.LEATHER
        );
    }

    @ParameterizedTest
    @CsvSource({
            "COTTON, 1",
            "WOOL, 2",
            "POLY, 3",
            "LINEN, 4",
            "LEATHER, 5"
    })
    @DisplayName("각 Material은 올바른 index를 반환")
    void getIndex_returnsCorrectValue(Material material, int expectedIndex) {
        assertThat(material.getIndex()).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @EnumSource(Material.class)
    @DisplayName("모든 Material의 index는 양수")
    void getIndex_forAllMaterials_isPositive(Material material) {
        assertThat(material.getIndex()).isPositive();
    }

    @Test
    @DisplayName("Material.valueOf()로 문자열에서 enum 변환 가능")
    void valueOf_withValidString_returnsEnum() {
        // when
        Material material = Material.valueOf("COTTON");

        // then
        assertThat(material).isEqualTo(Material.COTTON);
        assertThat(material.getIndex()).isEqualTo(1);
    }

    @Test
    @DisplayName("Material.valueOf()에 잘못된 문자열 입력 시 예외 발생")
    void valueOf_withInvalidString_throwsException() {
        assertThatThrownBy(() -> Material.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("각 Material의 index는 고유함")
    void allMaterials_haveUniqueIndices() {
        // when
        int[] indices = new int[Material.values().length];
        for (int i = 0; i < Material.values().length; i++) {
            indices[i] = Material.values()[i].getIndex();
        }

        // then
        assertThat(indices).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("Material enum은 순서가 정의되어 있음")
    void materialOrdering_isConsistent() {
        // when
        Material[] materials = Material.values();

        // then
        assertThat(materials[0]).isEqualTo(Material.COTTON);
        assertThat(materials[1]).isEqualTo(Material.WOOL);
        assertThat(materials[2]).isEqualTo(Material.POLY);
        assertThat(materials[3]).isEqualTo(Material.LINEN);
        assertThat(materials[4]).isEqualTo(Material.LEATHER);
    }

    @ParameterizedTest
    @EnumSource(Material.class)
    @DisplayName("모든 Material은 toString()으로 이름을 반환")
    void toString_returnsCorrectName(Material material) {
        assertThat(material.toString()).isNotEmpty();
    }

    @Test
    @DisplayName("동일한 Material 인스턴스는 같음")
    void sameMaterial_isEqual() {
        // given
        Material material1 = Material.COTTON;
        Material material2 = Material.COTTON;

        // then
        assertThat(material1).isEqualTo(material2);
        assertThat(material1).isSameAs(material2);
    }

    @Test
    @DisplayName("다른 Material 인스턴스는 다름")
    void differentMaterials_areNotEqual() {
        assertThat(Material.COTTON).isNotEqualTo(Material.WOOL);
    }
}