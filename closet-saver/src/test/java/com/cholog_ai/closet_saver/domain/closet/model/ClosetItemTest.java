package com.cholog_ai.closet_saver.domain.closet.model;

import com.cholog_ai.closet_saver.domain.closet.model.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ClosetItem 도메인 모델 테스트")
class ClosetItemTest {

    private ClosetItem closetItem;
    private double[] testEmbedding;

    @BeforeEach
    void setUp() {
        testEmbedding = new double[]{0.123, -0.442, 0.556, -0.789};
        closetItem = new ClosetItem(
                1L,
                "/uploads/knit_white.jpg",
                Color.WHITE,
                Category.KNIT,
                Season.WINTER,
                Material.WOOL,
                Style.MINIMAL,
                testEmbedding
        );
    }

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTests {

        @Test
        @DisplayName("모든 필드를 포함한 생성자로 객체 생성 성공")
        void constructor_withAllFields_createsObjectSuccessfully() {
            // given & when
            ClosetItem item = new ClosetItem(
                    100L,
                    "/test/image.jpg",
                    Color.BLACK,
                    Category.OUTER,
                    Season.FALL,
                    Material.COTTON,
                    Style.CASUAL,
                    new double[]{1.0, 2.0, 3.0}
            );

            // then
            assertThat(item.getId()).isEqualTo(100L);
            assertThat(item.getImageUrl()).isEqualTo("/test/image.jpg");
            assertThat(item.getColor()).isEqualTo(Color.BLACK);
            assertThat(item.getCategory()).isEqualTo(Category.OUTER);
            assertThat(item.getSeason()).isEqualTo(Season.FALL);
            assertThat(item.getMaterial()).isEqualTo(Material.COTTON);
            assertThat(item.getStyle()).isEqualTo(Style.CASUAL);
            assertThat(item.getEmbedding()).containsExactly(1.0, 2.0, 3.0);
        }

        @Test
        @DisplayName("기본 생성자로 객체 생성 성공")
        void noArgsConstructor_createsObject() {
            // when
            ClosetItem item = new ClosetItem();

            // then
            assertThat(item).isNotNull();
            assertThat(item.getId()).isNull();
            assertThat(item.getImageUrl()).isNull();
            assertThat(item.getColor()).isNull();
            assertThat(item.getCategory()).isNull();
            assertThat(item.getSeason()).isNull();
            assertThat(item.getMaterial()).isNull();
            assertThat(item.getStyle()).isNull();
            assertThat(item.getEmbedding()).isNull();
        }

        @Test
        @DisplayName("null ID로 생성 가능")
        void constructor_withNullId_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    null,
                    "/test/image.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{1.0}
            );

            // then
            assertThat(item.getId()).isNull();
            assertThat(item.getImageUrl()).isEqualTo("/test/image.jpg");
        }

        @Test
        @DisplayName("빈 embedding 배열로 생성 가능")
        void constructor_withEmptyEmbedding_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    1L,
                    "/test/image.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{}
            );

            // then
            assertThat(item.getEmbedding()).isEmpty();
        }

        @Test
        @DisplayName("null embedding으로 생성 가능")
        void constructor_withNullEmbedding_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    1L,
                    "/test/image.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    null
            );

            // then
            assertThat(item.getEmbedding()).isNull();
        }
    }

    @Nested
    @DisplayName("Getter 메서드 테스트")
    class GetterTests {

        @Test
        @DisplayName("getId()는 설정된 ID를 반환")
        void getId_returnsCorrectId() {
            assertThat(closetItem.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("getImageUrl()은 설정된 이미지 URL을 반환")
        void getImageUrl_returnsCorrectUrl() {
            assertThat(closetItem.getImageUrl()).isEqualTo("/uploads/knit_white.jpg");
        }

        @Test
        @DisplayName("getColor()는 설정된 색상을 반환")
        void getColor_returnsCorrectColor() {
            assertThat(closetItem.getColor()).isEqualTo(Color.WHITE);
        }

        @Test
        @DisplayName("getCategory()는 설정된 카테고리를 반환")
        void getCategory_returnsCorrectCategory() {
            assertThat(closetItem.getCategory()).isEqualTo(Category.KNIT);
        }

        @Test
        @DisplayName("getSeason()은 설정된 시즌을 반환")
        void getSeason_returnsCorrectSeason() {
            assertThat(closetItem.getSeason()).isEqualTo(Season.WINTER);
        }

        @Test
        @DisplayName("getMaterial()은 설정된 소재를 반환")
        void getMaterial_returnsCorrectMaterial() {
            assertThat(closetItem.getMaterial()).isEqualTo(Material.WOOL);
        }

        @Test
        @DisplayName("getStyle()은 설정된 스타일을 반환")
        void getStyle_returnsCorrectStyle() {
            assertThat(closetItem.getStyle()).isEqualTo(Style.MINIMAL);
        }

        @Test
        @DisplayName("getEmbedding()은 설정된 임베딩 배열을 반환")
        void getEmbedding_returnsCorrectEmbedding() {
            assertThat(closetItem.getEmbedding()).containsExactly(testEmbedding);
        }
    }

    @Nested
    @DisplayName("matchesColor() 메서드 테스트")
    class MatchesColorTests {

        @Test
        @DisplayName("동일한 색상이면 true 반환")
        void matchesColor_withSameColor_returnsTrue() {
            assertThat(closetItem.matchesColor(Color.WHITE)).isTrue();
        }

        @Test
        @DisplayName("다른 색상이면 false 반환")
        void matchesColor_withDifferentColor_returnsFalse() {
            assertThat(closetItem.matchesColor(Color.BLACK)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(Color.class)
        @DisplayName("모든 Color enum 값에 대해 정확히 비교")
        void matchesColor_withAllColorValues_comparesCorrectly(Color color) {
            // given
            ClosetItem item = new ClosetItem(
                    1L, "/test.jpg", color, Category.KNIT, 
                    Season.SPRING, Material.COTTON, Style.MINIMAL, new double[]{1.0}
            );

            // when & then
            assertThat(item.matchesColor(color)).isTrue();
        }

        @Test
        @DisplayName("null 색상과 비교 시 NullPointerException 발생")
        void matchesColor_withNullColor_throwsNullPointerException() {
            assertThatThrownBy(() -> closetItem.matchesColor(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("matchesCategory() 메서드 테스트")
    class MatchesCategoryTests {

        @Test
        @DisplayName("동일한 카테고리면 true 반환")
        void matchesCategory_withSameCategory_returnsTrue() {
            assertThat(closetItem.matchesCategory(Category.KNIT)).isTrue();
        }

        @Test
        @DisplayName("다른 카테고리면 false 반환")
        void matchesCategory_withDifferentCategory_returnsFalse() {
            assertThat(closetItem.matchesCategory(Category.OUTER)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(Category.class)
        @DisplayName("모든 Category enum 값에 대해 정확히 비교")
        void matchesCategory_withAllCategoryValues_comparesCorrectly(Category category) {
            // given
            ClosetItem item = new ClosetItem(
                    1L, "/test.jpg", Color.WHITE, category,
                    Season.SPRING, Material.COTTON, Style.MINIMAL, new double[]{1.0}
            );

            // when & then
            assertThat(item.matchesCategory(category)).isTrue();
        }

        @Test
        @DisplayName("null 카테고리와 비교 시 NullPointerException 발생")
        void matchesCategory_withNullCategory_throwsNullPointerException() {
            assertThatThrownBy(() -> closetItem.matchesCategory(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("matchesSeason() 메서드 테스트")
    class MatchesSeasonTests {

        @Test
        @DisplayName("동일한 시즌이면 true 반환")
        void matchesSeason_withSameSeason_returnsTrue() {
            assertThat(closetItem.matchesSeason(Season.WINTER)).isTrue();
        }

        @Test
        @DisplayName("다른 시즌이면 false 반환")
        void matchesSeason_withDifferentSeason_returnsFalse() {
            assertThat(closetItem.matchesSeason(Season.SUMMER)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(Season.class)
        @DisplayName("모든 Season enum 값에 대해 정확히 비교")
        void matchesSeason_withAllSeasonValues_comparesCorrectly(Season season) {
            // given
            ClosetItem item = new ClosetItem(
                    1L, "/test.jpg", Color.WHITE, Category.KNIT,
                    season, Material.COTTON, Style.MINIMAL, new double[]{1.0}
            );

            // when & then
            assertThat(item.matchesSeason(season)).isTrue();
        }

        @Test
        @DisplayName("null 시즌과 비교 시 NullPointerException 발생")
        void matchesSeason_withNullSeason_throwsNullPointerException() {
            assertThatThrownBy(() -> closetItem.matchesSeason(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("matchesMaterial() 메서드 테스트")
    class MatchesMaterialTests {

        @Test
        @DisplayName("동일한 소재면 true 반환")
        void matchesMaterial_withSameMaterial_returnsTrue() {
            assertThat(closetItem.matchesMaterial(Material.WOOL)).isTrue();
        }

        @Test
        @DisplayName("다른 소재면 false 반환")
        void matchesMaterial_withDifferentMaterial_returnsFalse() {
            assertThat(closetItem.matchesMaterial(Material.COTTON)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(Material.class)
        @DisplayName("모든 Material enum 값에 대해 정확히 비교")
        void matchesMaterial_withAllMaterialValues_comparesCorrectly(Material material) {
            // given
            ClosetItem item = new ClosetItem(
                    1L, "/test.jpg", Color.WHITE, Category.KNIT,
                    Season.SPRING, material, Style.MINIMAL, new double[]{1.0}
            );

            // when & then
            assertThat(item.matchesMaterial(material)).isTrue();
        }

        @Test
        @DisplayName("null 소재와 비교 시 NullPointerException 발생")
        void matchesMaterial_withNullMaterial_throwsNullPointerException() {
            assertThatThrownBy(() -> closetItem.matchesMaterial(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("matchesStyle() 메서드 테스트")
    class MatchesStyleTests {

        @Test
        @DisplayName("동일한 스타일이면 true 반환")
        void matchesStyle_withSameStyle_returnsTrue() {
            assertThat(closetItem.matchesStyle(Style.MINIMAL)).isTrue();
        }

        @Test
        @DisplayName("다른 스타일이면 false 반환")
        void matchesStyle_withDifferentStyle_returnsFalse() {
            assertThat(closetItem.matchesStyle(Style.CASUAL)).isFalse();
        }

        @ParameterizedTest
        @EnumSource(Style.class)
        @DisplayName("모든 Style enum 값에 대해 정확히 비교")
        void matchesStyle_withAllStyleValues_comparesCorrectly(Style style) {
            // given
            ClosetItem item = new ClosetItem(
                    1L, "/test.jpg", Color.WHITE, Category.KNIT,
                    Season.SPRING, Material.COTTON, style, new double[]{1.0}
            );

            // when & then
            assertThat(item.matchesStyle(style)).isTrue();
        }

        @Test
        @DisplayName("null 스타일과 비교 시 NullPointerException 발생")
        void matchesStyle_withNullStyle_throwsNullPointerException() {
            assertThatThrownBy(() -> closetItem.matchesStyle(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("toAttributeVector() 메서드 테스트")
    class ToAttributeVectorTests {

        @Test
        @DisplayName("속성 벡터를 올바른 순서로 반환")
        void toAttributeVector_returnsCorrectVector() {
            // when
            double[] vector = closetItem.toAttributeVector();

            // then
            assertThat(vector).hasSize(5);
            assertThat(vector[0]).isEqualTo(Color.WHITE.getIndex());
            assertThat(vector[1]).isEqualTo(Category.KNIT.getIndex());
            assertThat(vector[2]).isEqualTo(Season.WINTER.getIndex());
            assertThat(vector[3]).isEqualTo(Material.WOOL.getIndex());
            assertThat(vector[4]).isEqualTo(Style.MINIMAL.getIndex());
        }

        @Test
        @DisplayName("다양한 속성 조합에 대해 정확한 벡터 생성")
        void toAttributeVector_withDifferentAttributes_createsCorrectVector() {
            // given
            ClosetItem item = new ClosetItem(
                    1L,
                    "/test.jpg",
                    Color.BLACK,
                    Category.OUTER,
                    Season.FALL,
                    Material.LEATHER,
                    Style.STREET,
                    new double[]{1.0}
            );

            // when
            double[] vector = item.toAttributeVector();

            // then
            assertThat(vector).containsExactly(
                    (double) Color.BLACK.getIndex(),
                    (double) Category.OUTER.getIndex(),
                    (double) Season.FALL.getIndex(),
                    (double) Material.LEATHER.getIndex(),
                    (double) Style.STREET.getIndex()
            );
        }

        @Test
        @DisplayName("호출할 때마다 새로운 배열 인스턴스 반환")
        void toAttributeVector_returnsNewArrayInstance() {
            // when
            double[] vector1 = closetItem.toAttributeVector();
            double[] vector2 = closetItem.toAttributeVector();

            // then
            assertThat(vector1).isNotSameAs(vector2);
            assertThat(vector1).containsExactly(vector2);
        }

        @Test
        @DisplayName("모든 enum 조합에 대해 5개 요소 벡터 생성")
        void toAttributeVector_withAllEnumCombinations_creates5ElementVector() {
            // given
            for (Color color : Color.values()) {
                for (Category category : Category.values()) {
                    ClosetItem item = new ClosetItem(
                            1L, "/test.jpg", color, category,
                            Season.SPRING, Material.COTTON, Style.MINIMAL, new double[]{1.0}
                    );

                    // when
                    double[] vector = item.toAttributeVector();

                    // then
                    assertThat(vector).hasSize(5);
                }
            }
        }
    }

    @Nested
    @DisplayName("통합 시나리오 테스트")
    class IntegrationScenarioTests {

        @Test
        @DisplayName("여러 속성을 동시에 매칭할 수 있음")
        void multipleAttributeMatches_workCorrectly() {
            // given
            ClosetItem whiteWinterKnit = new ClosetItem(
                    1L, "/test.jpg", Color.WHITE, Category.KNIT,
                    Season.WINTER, Material.WOOL, Style.MINIMAL, new double[]{1.0}
            );

            // when & then
            assertThat(whiteWinterKnit.matchesColor(Color.WHITE)).isTrue();
            assertThat(whiteWinterKnit.matchesSeason(Season.WINTER)).isTrue();
            assertThat(whiteWinterKnit.matchesCategory(Category.KNIT)).isTrue();
            assertThat(whiteWinterKnit.matchesMaterial(Material.WOOL)).isTrue();
            assertThat(whiteWinterKnit.matchesStyle(Style.MINIMAL)).isTrue();
        }

        @Test
        @DisplayName("속성 벡터와 매칭 메서드는 일관된 결과를 제공")
        void attributeVectorAndMatchMethods_areConsistent() {
            // when
            double[] vector = closetItem.toAttributeVector();

            // then
            assertThat(vector[0]).isEqualTo(closetItem.getColor().getIndex());
            assertThat(vector[1]).isEqualTo(closetItem.getCategory().getIndex());
            assertThat(vector[2]).isEqualTo(closetItem.getSeason().getIndex());
            assertThat(vector[3]).isEqualTo(closetItem.getMaterial().getIndex());
            assertThat(vector[4]).isEqualTo(closetItem.getStyle().getIndex());
        }

        @Test
        @DisplayName("동일한 속성을 가진 두 아이템은 같은 속성 벡터를 가짐")
        void twoItemsWithSameAttributes_haveSameAttributeVector() {
            // given
            ClosetItem item1 = new ClosetItem(
                    1L, "/test1.jpg", Color.WHITE, Category.KNIT,
                    Season.WINTER, Material.WOOL, Style.MINIMAL, new double[]{1.0}
            );
            ClosetItem item2 = new ClosetItem(
                    2L, "/test2.jpg", Color.WHITE, Category.KNIT,
                    Season.WINTER, Material.WOOL, Style.MINIMAL, new double[]{2.0}
            );

            // when
            double[] vector1 = item1.toAttributeVector();
            double[] vector2 = item2.toAttributeVector();

            // then
            assertThat(vector1).containsExactly(vector2);
        }
    }

    @Nested
    @DisplayName("엣지 케이스 테스트")
    class EdgeCaseTests {

        @Test
        @DisplayName("매우 큰 ID 값으로 생성 가능")
        void constructor_withVeryLargeId_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    Long.MAX_VALUE,
                    "/test.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{1.0}
            );

            // then
            assertThat(item.getId()).isEqualTo(Long.MAX_VALUE);
        }

        @Test
        @DisplayName("음수 ID 값으로 생성 가능")
        void constructor_withNegativeId_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    -1L,
                    "/test.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{1.0}
            );

            // then
            assertThat(item.getId()).isEqualTo(-1L);
        }

        @Test
        @DisplayName("빈 문자열 imageUrl로 생성 가능")
        void constructor_withEmptyImageUrl_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    1L,
                    "",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{1.0}
            );

            // then
            assertThat(item.getImageUrl()).isEmpty();
        }

        @Test
        @DisplayName("매우 큰 embedding 배열로 생성 가능")
        void constructor_withLargeEmbedding_createsObject() {
            // given
            double[] largeEmbedding = new double[10000];
            for (int i = 0; i < largeEmbedding.length; i++) {
                largeEmbedding[i] = Math.random();
            }

            // when
            ClosetItem item = new ClosetItem(
                    1L,
                    "/test.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    largeEmbedding
            );

            // then
            assertThat(item.getEmbedding()).hasSize(10000);
        }

        @Test
        @DisplayName("극단적인 값의 embedding 배열로 생성 가능")
        void constructor_withExtremeEmbeddingValues_createsObject() {
            // when
            ClosetItem item = new ClosetItem(
                    1L,
                    "/test.jpg",
                    Color.WHITE,
                    Category.KNIT,
                    Season.SPRING,
                    Material.COTTON,
                    Style.MINIMAL,
                    new double[]{Double.MAX_VALUE, Double.MIN_VALUE, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}
            );

            // then
            assertThat(item.getEmbedding()).hasSize(4);
        }
    }
}